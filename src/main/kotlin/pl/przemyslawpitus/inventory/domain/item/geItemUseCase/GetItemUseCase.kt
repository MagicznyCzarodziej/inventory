package pl.przemyslawpitus.inventory.domain.item.geItemUseCase

import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.item.ItemProvider
import pl.przemyslawpitus.inventory.domain.user.UserId

class GetItemUseCase(
    private val itemProvider: ItemProvider,
) {
    fun getItem(itemId: ItemId, userId: UserId): Item {
        return itemProvider.getByIdForUser(
            itemId = itemId,
            userId = userId,
        )
    }
}