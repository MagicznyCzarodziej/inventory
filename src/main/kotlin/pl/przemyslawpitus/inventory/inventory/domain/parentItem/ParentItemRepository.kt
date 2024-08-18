package pl.przemyslawpitus.inventory.inventory.domain.parentItem

import pl.przemyslawpitus.inventory.common.domain.user.UserId

interface ParentItemRepository {
    fun save(parentItem: ParentItem): ParentItem
    fun getById(parentItemId: ParentItemId): ParentItem?
    fun getByUserId(userId: UserId): List<ParentItem>
    fun getAll(): List<ParentItem>
    fun removeById(parentItemId: ParentItemId)
}