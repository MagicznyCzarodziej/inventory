package pl.przemyslawpitus.inventory.domain.item

interface ItemRepository {
    fun getById(itemId: ItemId): Item?
    fun getAll(): List<Item>
    fun save(item: Item): Item
    fun removeById(itemId: ItemId)
}