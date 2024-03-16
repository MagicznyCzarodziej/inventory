package pl.przemyslawpitus.inventory.inventory.domain.item.geItemUseCase

import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemProvider
import pl.przemyslawpitus.inventory.common.domain.user.UserId

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