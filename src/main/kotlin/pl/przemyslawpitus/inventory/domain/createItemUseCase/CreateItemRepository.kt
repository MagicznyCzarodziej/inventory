package pl.przemyslawpitus.inventory.domain.createItemUseCase

import pl.przemyslawpitus.inventory.domain.item.Item

interface CreateItemRepository {
    fun createItem(item: Item): Item
}