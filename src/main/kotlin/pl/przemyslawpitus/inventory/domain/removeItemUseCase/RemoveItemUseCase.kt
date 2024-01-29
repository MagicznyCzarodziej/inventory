package pl.przemyslawpitus.inventory.domain.removeItemUseCase

import pl.przemyslawpitus.inventory.domain.ItemRepository
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.logging.WithLogger

class RemoveItemUseCase(
    private val itemRepository: ItemRepository,
) {
    fun removeItem(itemId: ItemId) {
        itemRepository.removeById(itemId)
        logger.domain("Removed item | $itemId")
    }

    private companion object : WithLogger()
}