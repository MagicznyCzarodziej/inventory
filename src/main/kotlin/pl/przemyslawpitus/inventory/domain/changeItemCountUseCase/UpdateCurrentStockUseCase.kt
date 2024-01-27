package pl.przemyslawpitus.inventory.domain.changeItemCountUseCase

import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.ItemRepository
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class UpdateCurrentStockUseCase(
    private val itemRepository: ItemRepository,
    private val stockTransformer: StockTransformer,
) {
    fun updateCurrentStock(itemId: ItemId, stockChange: Int): Item {
        logger.domain("Update current stock | $itemId")

        val item = itemRepository.getById(itemId) ?: throw RuntimeException("Item $itemId not found")

        val updatedStock = stockTransformer.updateCurrentStock(
            stock = item.stock,
            stockChange = stockChange,
        )

        val updatedItem = item.copy(
            stock = updatedStock,
            updatedAt = Instant.now(),
        )

        val savedItem = itemRepository.save(updatedItem)
        logger.domain("Saved item | ${item.id}")
        return savedItem
    }

    private companion object : WithLogger()
}
