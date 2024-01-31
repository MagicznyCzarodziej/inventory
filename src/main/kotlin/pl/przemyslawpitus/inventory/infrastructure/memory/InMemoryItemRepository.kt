package pl.przemyslawpitus.inventory.infrastructure.memory

import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemPhoto
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.domain.item.StockHistoryEntry
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import java.time.Instant

class InMemoryItemRepository : ItemRepository {
    private val items = mutableMapOf<ItemId, Item>(
        item1.id to item1,
        item2.id to item2,
        item3.id to item3,
        item4.id to item4,
        item5.id to item5,
    )

    override fun getById(itemId: ItemId): Item? {
        return items[itemId]
    }

    override fun save(item: Item): Item {
        items[item.id] = item
        return item
    }

    override fun removeById(itemId: ItemId) {
        if (!items.containsKey(itemId)) throw RuntimeException("Item $itemId not found")
        items.remove(itemId)
    }

    override fun getAll(): List<Item> {
        return items.values.toList()
    }
}

val item1 = Item(
    id = ItemId(value = randomUuid()),
    name = "Łagodny",
    description = "Z dużym korkiem",
    root = Root.ParentRoot(
        parentItem = parent2,
    ),
    brand = "Pudliszki",
    barcode = null,
    photo = ItemPhoto(
        id = PhotoId(randomUuid()),
        url = "https://www.carrefour.pl/images/product/org/pudliszki-ketchup-lagodny-990-g-bb0tvn.jpg",
    ),
    stock = Stock(
        currentStock = 1,
        desiredStock = 1,
        stockHistory = listOf(
            StockHistoryEntry(
                change = 1,
                stockAfterChange = 1,
                createdAt = Instant.now(),
            ),
        )
    ),
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
    removedAt = null,
    version = 0
)

val item2 = Item(
    id = ItemId(value = randomUuid()),
    name = "Pikantny",
    description = "Z dużym korkiem",
    root = Root.ParentRoot(
        parentItem = parent2,
    ),
    brand = "Pudliszki",
    barcode = null,
    photo = null,
    stock = Stock(
        currentStock = 1,
        desiredStock = 1,
        stockHistory = listOf(
            StockHistoryEntry(
                change = 1,
                stockAfterChange = 1,
                createdAt = Instant.now(),
            ),
        )
    ),
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
    removedAt = null,
    version = 0
)

val item3 = Item(
    id = ItemId(value = randomUuid()),
    name = "60l",
    description = "Zapach lawendowy",
    root = Root.ParentRoot(
        parentItem = parent1,
    ),
    brand = "Purio",
    barcode = null,
    photo = ItemPhoto(
        id = PhotoId(randomUuid()),
        url = "https://a.allegroimg.com/s512/11b1b8/d2a770064357af0dfe7b7ae01b14/Worki-z-tasma-35l-na-smieci-15-szt"
    ),
    stock = Stock(
        currentStock = 2,
        desiredStock = 5,
        stockHistory = listOf(
            StockHistoryEntry(
                change = 2,
                stockAfterChange = 2,
                createdAt = Instant.now(),
            ),
        )
    ),
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
    removedAt = null,
    version = 0
)

val item4 = Item(
    id = ItemId(value = randomUuid()),
    name = "Masło",
    description = null,
    root = Root.CategoryRoot(
        category = foodCategory,
    ),
    brand = "Mleczna dolina",
    barcode = "05954186451",
    photo = ItemPhoto(
        id = PhotoId(randomUuid()),
        url = "https://www.dine4fit.pl/file/image/foodstuff/53b83bde708c45398001dbec9ddb79f6/381ffa4ece244078b5f14c3621af6648"
    ),
    stock = Stock(
        currentStock = 1,
        desiredStock = 1,
        stockHistory = listOf(
            StockHistoryEntry(
                change = 1,
                stockAfterChange = 1,
                createdAt = Instant.now(),
            ),
        )
    ),
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
    removedAt = null,
    version = 0
)

val item5 = Item(
    id = ItemId(value = randomUuid()),
    name = "Majonez",
    description = null,
    root = Root.CategoryRoot(
        category = foodCategory,
    ),
    brand = "Winiary",
    barcode = "05954186451",
    photo = ItemPhoto(
        id = PhotoId(randomUuid()),
        url = "https://www.dine4fit.pl/file/image/foodstuff/53b83bde708c45398001dbec9ddb79f6/381ffa4ece244078b5f14c3621af6648"
    ),
    stock = Stock(
        currentStock = 0,
        desiredStock = 1,
        stockHistory = listOf(
            StockHistoryEntry(
                change = 0,
                stockAfterChange = 0,
                createdAt = Instant.now(),
            ),
        )
    ),
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
    removedAt = null,
    version = 0
)