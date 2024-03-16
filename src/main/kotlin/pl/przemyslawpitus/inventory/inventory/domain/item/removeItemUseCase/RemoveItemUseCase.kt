package pl.przemyslawpitus.inventory.inventory.domain.item.removeItemUseCase

import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemProvider
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.logging.WithLogger

class RemoveItemUseCase(
    private val itemRepository: ItemRepository,
    private val itemProvider: ItemProvider,
) {
    fun removeItem(itemId: ItemId, userId: UserId) {
        // Check if item exists and belongs to user
        itemProvider.getByIdForUser(
            itemId = itemId,
            userId = userId,
        )

        itemRepository.removeById(itemId)

        logger.domain("Removed item | $itemId")
    }

    private companion object : WithLogger()
}