package pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemsUseCase

import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.common.domain.user.UserId

class GetParentItemsUseCase(
    private val parentItemRepository: ParentItemRepository,
) {
    fun getParentItems(userId: UserId): List<ParentItem> {
        return parentItemRepository.getByUserId(userId)
    }
}