package pl.przemyslawpitus.inventory.inventory.api.item

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.inventory.domain.item.geItemUseCase.GetItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemNotFound
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class GetItemEndpoint(
    private val getItemUseCase: GetItemUseCase,
    private val errorHandler: ErrorHandler,
) {
    @GetMapping(
        "/items/{itemId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getItem(
        @PathVariable itemId: String,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Get item | $itemId")

        try {
            val item = getItemUseCase.getItem(
                itemId = ItemId(itemId),
                userId = userDetails.id,
            )

            return ResponseEntity.ok(
                item.toGetItemResponse()
            )
        } catch (exception: ItemNotFound) {
            return handleItemNotFound(exception)
        } catch (exception: ItemDoesNotBelongToUser) {
            return handleItemNotFound(exception)
        }
    }

    private fun handleItemNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "ITEM_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "Item not found",
            exception = exception,
        )

    private companion object : WithLogger()
}

data class GetItemResponse(
    val id: String,
    val name: String,
    val description: String?,
    val category: Category,
    val parentItem: ParentItem?,
    val brand: String?,
    val currentStock: Int,
    val desiredStock: Int,
    val lastRestockedAt: String?,
    val photoUrl: String?,
    val barcode: String?,
) {
    data class Category(
        val id: String,
        val name: String,
    )

    data class ParentItem(
        val id: String,
        val name: String,
    )
}

private fun Item.toGetItemResponse() = GetItemResponse(
    id = id.value,
    name = name,
    description = description,
    category = getCategory(),
    parentItem = getParentItem(),
    brand = brand,
    currentStock = stock.currentStock,
    desiredStock = stock.desiredStock,
    lastRestockedAt = getLastRestockedAt()?.toString(),
    photoUrl = photo?.url,
    barcode = barcode,
)

private fun Item.getCategory(): GetItemResponse.Category =
    when (this.root) {
        is Root.CategoryRoot -> GetItemResponse.Category(
            id = this.root.category.id.value,
            name = this.root.category.name,
        )

        is Root.ParentRoot -> GetItemResponse.Category(
            id = this.root.parentItem.category.id.value,
            name = this.root.parentItem.category.name,
        )
    }

private fun Item.getParentItem(): GetItemResponse.ParentItem? =
    when (this.root) {
        is Root.ParentRoot -> GetItemResponse.ParentItem(
            id = this.root.parentItem.id.value,
            name = this.root.parentItem.name,
        )

        else -> null
    }

private fun Item.getLastRestockedAt() =
    stock
        .stockHistory
        .findLast { it.change > 0 }
        ?.createdAt