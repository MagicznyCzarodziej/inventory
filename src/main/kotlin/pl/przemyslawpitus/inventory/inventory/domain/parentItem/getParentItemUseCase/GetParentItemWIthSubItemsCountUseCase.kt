package pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemProvider

class GetParentItemWIthSubItemsCountUseCase(
    private val parentItemProvider: ParentItemProvider,
    private val itemRepository: ItemRepository,
) {
    fun getParentItem(parentItemId: ParentItemId, userId: UserId): ParentItemWithSubItemsCount {
        val parentItem = parentItemProvider.getByIdForUser(
            parentItemId = parentItemId,
            userId = userId,
        )

        val subItemsCount = itemRepository.countByParentItemId(parentItemId)

        return ParentItemWithSubItemsCount(
            id = parentItem.id,
            name = parentItem.name,
            category = parentItem.category,
            subItemsCount = subItemsCount,
        )
    }
}

data class ParentItemWithSubItemsCount(
    val id: ParentItemId,
    val name: String,
    val category: Category,
    val subItemsCount: Long,
)
