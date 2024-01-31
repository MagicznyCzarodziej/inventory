package pl.przemyslawpitus.inventory.infrastructure.memory

import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import java.time.Instant

class InMemoryParentItemRepository : ParentItemRepository {
    private val parentItems = mutableMapOf<ParentItemId, ParentItem>(
        parent1.id to parent1,
        parent2.id to parent2,
    )

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

val parent1 = ParentItem(
    id = ParentItemId(randomUuid()),
    name = "Worki na śmieci",
    category = cleaningCategory,
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
    removedAt = null,
    version = 0,
)


val parent2 = ParentItem(
    id = ParentItemId(randomUuid()),
    name = "Ketchup",
    category = foodCategory,
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
    removedAt = null,
    version = 0,
)