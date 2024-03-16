package pl.przemyslawpitus.inventory.inventory.domain.item

import pl.przemyslawpitus.inventory.common.domain.user.UserId

class ItemProvider(
    private val itemRepository: ItemRepository,
) {
    fun getByIdForUser(itemId: ItemId, userId: UserId): Item {
        val item = itemRepository.getById(itemId)
            ?: throw ItemNotFound(itemId)

        if (item.userId != userId) {
            throw ItemDoesNotBelongToUser(
                itemId = item.id,
                userId = userId
            )
        }

        return item
    }
}

class ItemNotFound(itemId: ItemId) : RuntimeException("Item not found $itemId")

class ItemDoesNotBelongToUser(itemId: ItemId, userId: UserId) :
    RuntimeException("Item ${itemId.value} doesn't belong to user ${userId.value}")
