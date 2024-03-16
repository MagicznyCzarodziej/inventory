package pl.przemyslawpitus.inventory.inventory.domain.item.editItemUseCase

import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemProvider
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.logging.WithLogger

class EditItemUseCase(
    private val itemRepository: ItemRepository,
    private val itemProvider: ItemProvider,
    private val editItemTransformer: EditItemTransformer,
) {
    fun editItem(editItemParameters: EditItemParameters, userId: UserId): Item {
        logger.domain("Edit item | ${editItemParameters.name}")

        val item = itemProvider.getByIdForUser(
            itemId = editItemParameters.id,
            userId = userId,
        )

        val updatedItem = editItemTransformer.editItem(
            item = item,
            editItemParameters = editItemParameters,
            userId = userId,
        )

        val savedItem = itemRepository.save(updatedItem)
        logger.domain("Saved item | ${item.id}")

        return savedItem
    }

    private companion object : WithLogger()
}

data class EditItemParameters(
    val id: ItemId,
    val name: String,
    val description: String?,
    val categoryId: CategoryId?,
    val brand: String?,
    val currentStock: Int,
    val desiredStock: Int,
    val photoId: pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId?,
    val barcode: String?,
)
