package pl.przemyslawpitus.inventory.inventory.domain.parentItem.removeParentItemUseCase

import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemProvider
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.logging.WithLogger

class RemoveParentItemUseCase(
    private val parentItemRepository: ParentItemRepository,
    private val parentItemProvider: ParentItemProvider,
    private val itemRepository: ItemRepository,
) {
    fun removeParentItem(parentItemId: ParentItemId, userId: UserId) {
        // Check if parentItem exists and belongs to user
        parentItemProvider.getByIdForUser(
            parentItemId = parentItemId,
            userId = userId,
        )

        val items = itemRepository.getByParentItemId(parentItemId)
        if (items.isNotEmpty()) {
            throw CannotDeleteParentItemWithSubItems(parentItemId)
        }

        parentItemRepository.removeById(parentItemId)
        logger.domain("Removed parent item | $parentItemId")
    }

    private companion object : WithLogger()
}

class CannotDeleteParentItemWithSubItems(parentItemId: ParentItemId) :
    RuntimeException("Cannot delete parent item $parentItemId, because it has subitems")