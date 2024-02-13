package pl.przemyslawpitus.inventory.domain.item

import pl.przemyslawpitus.inventory.domain.user.UserId

interface ItemRepository {
    fun getById(itemId: ItemId): Item?
    fun getByUserId(userId: UserId): List<Item>
    fun getAll(): List<Item>
    fun save(item: Item): Item
    fun removeById(itemId: ItemId)
}