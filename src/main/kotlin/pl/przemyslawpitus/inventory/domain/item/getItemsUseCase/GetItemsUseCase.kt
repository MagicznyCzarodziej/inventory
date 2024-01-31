package pl.przemyslawpitus.inventory.domain.item.getItemsUseCase

import pl.przemyslawpitus.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.domain.item.Root

class GetItemsUseCase(
    private val itemRepository: ItemRepository,
) {
    fun getItems(): List<ItemsView.Entry> {
        val items = itemRepository.getAll()
        val (independentItems, subItems) = items.partition { it.root is Root.CategoryRoot }

        val parentItemsToItems = subItems.groupBy { (it.root as Root.ParentRoot).parentItem }

        val groups =
            parentItemsToItems.map {
                ItemsView.Entry.ParentEntry(
                    parentItem = it.key,
                    items = it.value
                )
            } + independentItems.map {
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
