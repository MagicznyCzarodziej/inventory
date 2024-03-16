package pl.przemyslawpitus.inventory.inventory.domain.item.getItemsUseCase

import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.common.domain.user.UserId

class GetItemsUseCase(
    private val itemRepository: ItemRepository,
    private val parentItemRepository: ParentItemRepository,
) {
    fun getItems(userId: UserId): List<ItemsView.Entry> {
        val items = itemRepository.getByUserId(userId)
        val (independentItems, subItems) = items.partition { it.root is Root.CategoryRoot }

        val parentItems = parentItemRepository.getByUserId(userId)

        val parentsWithItems = parentItems.associateWith { parent ->
            subItems.filter {
                (it.root as Root.ParentRoot).parentItem.id == parent.id
            }
        }

        val parentEntries = parentsWithItems.map {
            ItemsView.Entry.ParentEntry(
                parentItem = it.key,
                items = it.value,
            )
        }

        val groups =
            parentEntries + independentItems.map {
                ItemsView.Entry.IndependentItemEntry(
                    item = it
                )
            }

        return groups
    }
}

data class ItemsView(
    val entries: List<Entry>,
) {
    sealed class Entry {
        data class ParentEntry(
            val parentItem: ParentItem,
            val items: List<Item>,
        ) : Entry()

        data class IndependentItemEntry(
            val item: Item,
        ) : Entry()
    }
}
