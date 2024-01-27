package pl.przemyslawpitus.inventory.infrastructure

import pl.przemyslawpitus.inventory.domain.ParentItemRepository
import pl.przemyslawpitus.inventory.domain.item.ParentItem
import pl.przemyslawpitus.inventory.domain.item.ParentItemId

class InMemoryParentItemRepository : ParentItemRepository {
    private val parentItems = mutableMapOf<ParentItemId, ParentItem>()

    override fun save(parentItem: ParentItem): ParentItem {
        if (parentItems.contains(parentItem.id)) throw RuntimeException("Cannot add item with the same id")
        parentItems[parentItem.id] = parentItem
        return parentItem
    }

    override fun getById(parentItemId: ParentItemId): ParentItem? {
        return parentItems[parentItemId]
    }

    override fun getAll(): List<ParentItem> {
        return parentItems.values.toList()
    }
}
