package pl.przemyslawpitus.inventory.domain.parentItem

interface ParentItemRepository {
    fun save(parentItem: ParentItem): ParentItem
    fun getById(parentItemId: ParentItemId): ParentItem?
    fun getAll(): List<ParentItem>
}