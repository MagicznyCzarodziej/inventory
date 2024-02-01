package pl.przemyslawpitus.inventory.domain.item.geItemUseCase

import pl.przemyslawpitus.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemId

class GetItemUseCase(
    private val itemRepository: ItemRepository,
) {
    fun getItem(itemId: ItemId): Item {
        val item = itemRepository.getById(itemId) ?: throw RuntimeException("Item $itemId not found")

        return item
    }
}