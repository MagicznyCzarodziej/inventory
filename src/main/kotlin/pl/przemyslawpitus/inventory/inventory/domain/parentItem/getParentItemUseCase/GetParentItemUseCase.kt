package pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemProvider

class GetParentItemUseCase(
    private val parentItemProvider: ParentItemProvider,
) {
    fun getParentItem(parentItemId: ParentItemId, userId: UserId): ParentItem {
        return parentItemProvider.getByIdForUser(
            parentItemId = parentItemId,
            userId = userId,
        )
    }
}