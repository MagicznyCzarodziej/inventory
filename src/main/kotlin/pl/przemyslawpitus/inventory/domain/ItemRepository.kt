package pl.przemyslawpitus.inventory.domain

import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemId

interface ItemRepository {
    fun getById(itemId: ItemId): Item?
    fun getAll(): List<Item>
    fun save(item: Item): Item
}