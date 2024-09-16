package pl.przemyslawpitus.inventory.inventory.domain.item

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId

interface ItemRepository {
    fun getById(itemId: ItemId): Item?
    fun getByUserId(userId: UserId): List<Item>
    fun getAll(): List<Item>
    fun getByParentItemId(parentItemId: ParentItemId): List<Item>
    fun countByParentItemId(parentItemId: ParentItemId): Long
    fun countByCategoryId(categoryId: CategoryId): Long
    fun save(item: Item): Item
    fun removeById(itemId: ItemId)
}