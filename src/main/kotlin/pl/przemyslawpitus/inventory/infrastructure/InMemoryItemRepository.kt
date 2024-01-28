package pl.przemyslawpitus.inventory.infrastructure

import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.ItemRepository
import pl.przemyslawpitus.inventory.domain.createItemUseCase.CreateItemRepository
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemPhoto
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.domain.item.StockHistoryEntry
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import java.time.Instant

class InMemoryItemRepository : ItemRepository, CreateItemRepository {
    private val items = mutableMapOf<ItemId, Item>(
        item1.id to item1,
        item2.id to item2,
        item3.id to item3,
        item4.id to item4,
    )

    override fun createItem(item: Item): Item {
        if (items.contains(item.id)) throw RuntimeException("Cannot add item with the same id")
        items[item.id] = item
        return item
    }

    override fun getById(itemId: ItemId): Item? {
        return items[itemId]
    }

    override fun save(item: Item): Item {
        items[item.id] = item
        return item
    }

    override fun getAll(): List<Item> {
        return items.values.toList()
    }
}

val item1 = Item(
    id = ItemId(value = randomUuid()),
    name = "Łagodny",
    description = null,
    root = Root.ParentRoot(
        parentItem = parent2,
    ),
    brand = "Pudliszki",
    barcode = null,
    photo = ItemPhoto(
        photoId = PhotoId(randomUuid()),
        photoUrl = "https://www.carrefour.pl/images/product/org/pudliszki-ketchup-lagodny-990-g-bb0tvn.jpg",
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
    description = null,
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
    description = null,
    root = Root.ParentRoot(
        parentItem = parent1,
    ),
    brand = "Purio",
    barcode = null,
    photo = ItemPhoto(
        photoId = PhotoId(randomUuid()),
        photoUrl = "https://a.allegroimg.com/s512/11b1b8/d2a770064357af0dfe7b7ae01b14/Worki-z-tasma-35l-na-smieci-15-szt"
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
        photoId = PhotoId(randomUuid()),
        photoUrl = "https://www.dine4fit.pl/file/image/foodstuff/53b83bde708c45398001dbec9ddb79f6/381ffa4ece244078b5f14c3621af6648"
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