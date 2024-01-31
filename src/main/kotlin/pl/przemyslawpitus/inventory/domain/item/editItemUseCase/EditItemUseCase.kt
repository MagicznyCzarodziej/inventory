package pl.przemyslawpitus.inventory.domain.item.editItemUseCase

import pl.przemyslawpitus.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class EditItemUseCase(
    private val itemRepository: ItemRepository,
) {
    fun editItem(editItemParameters: EditItemParameters): Item {
        logger.domain("Edit item | ${editItemParameters.name}")

        val item = itemRepository.getById(editItemParameters.id)
            ?: throw RuntimeException("Item ${editItemParameters.id} not found")

        if (!hasAnyFieldChanged(item, editItemParameters)) {
            logger.domain("Nothing changed | ${item.id}")
            return item
        }

        val updatedItem = item.copy(
            name = editItemParameters.name,
            description = editItemParameters.description,
            brand = editItemParameters.brand,
            stock = updateStock(currentItem = item, editItemParameters = editItemParameters),
            updatedAt = Instant.now(),
        )

        val savedItem = itemRepository.save(updatedItem)
        logger.domain("Saved item | ${item.id}")
        return savedItem
    }

    private fun hasAnyFieldChanged(currentItem: Item, editItemParameters: EditItemParameters) =
        currentItem.name != editItemParameters.name
                || currentItem.description != editItemParameters.description
                || currentItem.brand != editItemParameters.brand
                || currentItem.stock.desiredStock != editItemParameters.desiredStock

    private fun updateStock(currentItem: Item, editItemParameters: EditItemParameters) =
        Stock(
            currentStock = currentItem.stock.currentStock,
            desiredStock = editItemParameters.desiredStock,
            stockHistory = currentItem.stock.stockHistory,
        )

    private companion object : WithLogger()
}

data class EditItemParameters(
    val id: ItemId,
    val name: String,
    val description: String?,
    val brand: String?,
    val desiredStock: Int,
)