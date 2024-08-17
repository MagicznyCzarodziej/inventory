package pl.przemyslawpitus.inventory.inventory.domain.item.createItemUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.logging.WithLogger
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemPhoto
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.inventory.domain.item.StockHistoryEntry
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemProvider
import pl.przemyslawpitus.inventory.common.domain.utils.randomUuid
import java.time.Instant

class CreateItemUseCase(
    private val itemRepository: ItemRepository,
    private val categoryProvider: CategoryProvider,
    private val parentItemProvider: ParentItemProvider,
) {
    fun createItem(itemDraft: ItemDraft, userId: UserId): Item {
        logger.domain("Create item | ${itemDraft.name}")

        val now = Instant.now()
        val itemPhoto = itemDraft.photoId?.let { getPhoto(it) }
        val root = getRoot(itemDraft, userId)

        val item = Item(
            id = ItemId(value = randomUuid()),
            userId = userId,
            name = itemDraft.name,
            description = itemDraft.description,
            root = root,
            brand = itemDraft.brand,
            barcode = itemDraft.barcode,
            photo = itemPhoto,
            stock = createNewStock(
                currentStock = itemDraft.currentStock,
                desiredStock = itemDraft.desiredStock,
            ),
            createdAt = now,
            updatedAt = now,
            removedAt = null,
            version = 0,
        )

        val savedItem = itemRepository.save(item)
        logger.domain("Saved item | ${item.id}")
        return savedItem
    }

    private fun getPhoto(photoId: PhotoId): ItemPhoto =
        ItemPhoto(
            id = photoId,
            url = "/photos/${photoId.value}"
        )

    private fun getRoot(itemDraft: ItemDraft, userId: UserId): Root =
        when (itemDraft.itemType) {
            ItemDraft.ItemType.ITEM -> createCategoryRoot(itemDraft, userId)
            ItemDraft.ItemType.SUB_ITEM -> createParentItemRoot(itemDraft, userId)
        }

    private fun createCategoryRoot(
        itemDraft: ItemDraft,
        userId: UserId,
    ): Root.CategoryRoot {
        requireNotNull(itemDraft.categoryId)

        val category = categoryProvider.getByIdForUser(
            categoryId = itemDraft.categoryId,
            userId = userId
        )

        return Root.CategoryRoot(category)
    }

    private fun createParentItemRoot(
        itemDraft: ItemDraft,
        userId: UserId,
    ): Root.ParentRoot {
        requireNotNull(itemDraft.parentId)

        val parentItem = parentItemProvider.getByIdForUser(
            parentItemId = itemDraft.parentId,
            userId = userId,
        )

        return Root.ParentRoot(parentItem)
    }

    private fun createNewStock(currentStock: Int, desiredStock: Int) =
        Stock(
            currentStock = currentStock,
            desiredStock = desiredStock,
            stockHistory = listOf(
                StockHistoryEntry(
                    change = currentStock,
                    stockAfterChange = currentStock,
                    createdAt = Instant.now(),
                )
            )
        )

    private companion object : WithLogger()
}

data class ItemDraft(
    val itemType: ItemType,
    val name: String,
    val description: String?,
    val categoryId: CategoryId?,
    val parentId: ParentItemId?,
    val brand: String?,
    val barcode: String?,
    val photoId: PhotoId?,
    val currentStock: Int,
    val desiredStock: Int,
) {
    enum class ItemType {
        ITEM, SUB_ITEM
    }
}
