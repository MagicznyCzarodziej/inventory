package pl.przemyslawpitus.inventory.domain.createItemUseCase

import pl.przemyslawpitus.inventory.domain.CategoryRepository
import pl.przemyslawpitus.inventory.domain.ParentItemRepository
import pl.przemyslawpitus.inventory.logging.WithLogger
import pl.przemyslawpitus.inventory.domain.item.CategoryId
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.item.ItemPhoto
import pl.przemyslawpitus.inventory.domain.item.ParentItemId
import pl.przemyslawpitus.inventory.domain.item.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.domain.item.StockHistoryEntry
import pl.przemyslawpitus.inventory.domain.uploadPhotoUseCase.PhotoRepository
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import java.time.Instant

class CreateItemUseCase(
    private val createItemRepository: CreateItemRepository,
    private val categoryRepository: CategoryRepository,
    private val parentItemRepository: ParentItemRepository,
    private val photoRepository: PhotoRepository,
) {
    fun createItem(itemDraft: ItemDraft): Item {
        logger.domain("Create item | ${itemDraft.name}")

        val now = Instant.now()
        val itemPhoto = itemDraft.photoId?.let { getPhoto(it) }
        val root = getRoot(itemDraft)

        val item = Item(
            id = ItemId(value = randomUuid()),
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

        val savedItem = createItemRepository.createItem(item)
        logger.domain("Saved item | ${item.id}")
        return savedItem
    }

    private fun getPhoto(photoId: PhotoId): ItemPhoto {
//        val photo = photoRepository.getById(photoId) ?: throw RuntimeException("Photo $photoId not found")

        return ItemPhoto(
            photoId = photoId,
            photoUrl = "/photos/${photoId.value}"
        )
    }

    private fun getRoot(itemDraft: ItemDraft): Root =
        when (itemDraft.itemType) {
            ItemDraft.ItemType.ITEM -> {
                requireNotNull(itemDraft.categoryId)
                Root.CategoryRoot(
                    categoryRepository.getById(itemDraft.categoryId)
                        ?: throw RuntimeException("Category not found ${itemDraft.categoryId}")
                )
            }

            ItemDraft.ItemType.SUB_ITEM -> {
                requireNotNull(itemDraft.parentId)
                Root.ParentRoot(
                    parentItemRepository.getById(itemDraft.parentId)
                        ?: throw RuntimeException("ParentItem not found ${itemDraft.parentId}")
                )
            }
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