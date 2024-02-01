package pl.przemyslawpitus.inventory.api.item

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.logging.WithLogger
import pl.przemyslawpitus.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.createItemUseCase.CreateItemUseCase
import pl.przemyslawpitus.inventory.domain.item.createItemUseCase.ItemDraft
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.domain.item.PhotoId

@RestController
class CreateItemEndpoint(
    private val createItemUseCase: CreateItemUseCase,
) {
    @PostMapping(
        "/items",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createItem(
        @RequestBody request: CreateItemRequest,
    ): ResponseEntity<*> {
        logger.api("Create item | ${request.name}")

        val item = createItemUseCase.createItem(
            request.toItemDraft()
        )

        return ResponseEntity.ok(
            item.toCreateItemResponse()
        )
    }

    private companion object : WithLogger()
}

data class CreateItemRequest(
    val itemType: String,
    val name: String,
    val description: String,
    val categoryId: String?,
    val parentId: String?,
    val brand: String,
    val currentStock: Int,
    val desiredStock: Int,
    val photoId: String?,
    val barcode: String?,
) {
    fun toItemDraft(): ItemDraft = ItemDraft(
        itemType = ItemDraft.ItemType.valueOf(itemType),
        name = name,
        description = description,
        categoryId = CategoryId.orNull(value = categoryId),
        parentId = ParentItemId.orNull(value = parentId),
        brand = brand,
        barcode = barcode,
        photoId = PhotoId.orNull(photoId),
        currentStock = currentStock,
        desiredStock = desiredStock,
    )
}

data class CreateItemResponse(
    val id: String,
    val name: String,
)

private fun Item.toCreateItemResponse() =
    CreateItemResponse(
        id = this.id.value,
        name = this.name,
    )