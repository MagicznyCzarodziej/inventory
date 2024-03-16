package pl.przemyslawpitus.inventory.inventory.domain.item.changeItemCountUseCase

import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemProvider
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class UpdateCurrentStockUseCase(
    private val itemProvider: ItemProvider,
    private val itemRepository: ItemRepository,
    private val stockTransformer: StockTransformer,
) {
    fun updateCurrentStock(itemId: ItemId, stockChange: Int, userId: UserId): Item {
        logger.domain("Update current stock | $itemId")

        val item = itemProvider.getByIdForUser(
            itemId = itemId,
            userId = userId,
        )

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
