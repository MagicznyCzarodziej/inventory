package pl.przemyslawpitus.inventory.infrastructure

import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.ItemRepository
import pl.przemyslawpitus.inventory.domain.createItemUseCase.CreateItemRepository
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemPhoto
import pl.przemyslawpitus.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.domain.item.Stock
import java.time.Instant

class InMemoryItemRepository : ItemRepository, CreateItemRepository {
    private val items = mutableMapOf<ItemId, Item>()

    override fun createItem(item: Item): Item {
        if (items.contains(item.id)) throw RuntimeException("Cannot add item with the same id")
        items[item.id] = item
        return item
    }

    override fun getById(itemId: ItemId): Item? {
        return items[itemId]
    }

    override fun save(item: Item): Item {
        items[item.id] = item
        return item
    }

    override fun getAll(): List<Item> {
        return items.values.toList()
    }
}
