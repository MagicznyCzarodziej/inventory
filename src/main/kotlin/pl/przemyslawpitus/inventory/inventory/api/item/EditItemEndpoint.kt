package pl.przemyslawpitus.inventory.inventory.api.item

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase.EditItemParameters
import pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase.EditItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryNotFound
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemNotFound
import pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase.CannotEditCategoryInSubItem
import pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase.MissingCategoryId
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class EditItemEndpoint(
    private val editItemUseCase: EditItemUseCase,
    private val errorHandler: ErrorHandler,
) {
    @PutMapping(
        "/items/{itemId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun editItem(
        @PathVariable itemId: String,
        @RequestBody request: EditItemRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Edit item | $itemId")

        try {
            editItemUseCase.editItem(
                editItemParameters = request.toEditItemParameters(ItemId(itemId)),
                userId = userDetails.id,
            )

            return ResponseEntity.noContent().build<Unit>()
        } catch (exception: ItemNotFound) {
            return handleItemNotFound(exception)
        } catch (exception: ItemDoesNotBelongToUser) {
            return handleItemNotFound(exception)
        } catch (exception: MissingCategoryId) {
            return errorHandler.handleError(
                code = "MISSING_CATEGORY_ID",
                status = HttpStatus.BAD_REQUEST,
                message = exception.message!!,
                exception = exception,
            )
        } catch (exception: CannotEditCategoryInSubItem) {
            return errorHandler.handleError(
                code = "CANNOT_EDIT_CATEGORY_IN_SUB_ITEM",
                status = HttpStatus.BAD_REQUEST,
                message = exception.message!!,
                exception = exception,
            )
        } catch (exception: CategoryNotFound) {
            return handleCategoryNotFound(exception)
        } catch (exception: CategoryDoesNotBelongToUser) {
            return handleCategoryNotFound(exception)
        }
    }

    private fun handleCategoryNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "CATEGORY_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "Category not found",
            exception = exception,
        )

    private fun handleItemNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "ITEM_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "Item not found",
            exception = exception,
        )

    private companion object : WithLogger()
}

data class EditItemRequest(
    val name: String,
    val description: String?,
    val categoryId: String?,
    val brand: String?,
    val currentStock: Int,
    val desiredStock: Int,
    val photoId: String?,
    val barcode: String?,
) {
    fun toEditItemParameters(itemId: ItemId) = EditItemParameters(
        id = itemId,
        name = this.name,
        description = this.description,
        categoryId = CategoryId.orNull(this.categoryId),
        brand = this.brand,
        currentStock = this.currentStock,
        desiredStock = this.desiredStock,
        photoId = pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId.orNull(this.photoId),
        barcode = this.barcode,
    )
}
