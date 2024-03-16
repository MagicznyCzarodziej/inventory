package pl.przemyslawpitus.inventory.inventory.domain.parentItem

import pl.przemyslawpitus.inventory.common.domain.user.UserId

class ParentItemProvider(
    private val parentItemRepository: ParentItemRepository
) {
    fun getByIdForUser(parentItemId: ParentItemId, userId: UserId): ParentItem {
        val parentItem = parentItemRepository.getById(parentItemId)
            ?: throw ParentItemNotFound(parentItemId)

        if (parentItem.userId != userId) {
            throw ParentItemDoesNotBelongToUser(
                parentItemId = parentItem.id,
                userId = userId
            )
        }

        return parentItem
    }
}

class ParentItemNotFound(parentItemId: ParentItemId) : RuntimeException("Parent item not found $parentItemId")

class ParentItemDoesNotBelongToUser(parentItemId: ParentItemId, userId: UserId) :
    RuntimeException("Parent item ${parentItemId.value} doesn't belong to user ${userId.value}")
