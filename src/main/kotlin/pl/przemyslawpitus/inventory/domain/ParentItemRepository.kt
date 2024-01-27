package pl.przemyslawpitus.inventory.domain

import pl.przemyslawpitus.inventory.domain.item.ParentItem
import pl.przemyslawpitus.inventory.domain.item.ParentItemId

interface ParentItemRepository {
    fun save(parentItem: ParentItem): ParentItem
    fun getById(parentItemId: ParentItemId): ParentItem?
    fun getAll(): List<ParentItem>
}