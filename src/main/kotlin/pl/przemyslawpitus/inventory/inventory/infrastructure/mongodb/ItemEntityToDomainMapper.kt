package pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.inventory.domain.item.Stock
import pl.przemyslawpitus.inventory.inventory.domain.item.StockHistoryEntry

class ItemEntityToDomainMapper(
    private val categoryRepository: CategoryRepository,
    private val parentItemRepository: ParentItemRepository,
) {
    fun mapToDomain(entity: ItemEntity): Item {
        val root = when (entity.root.type) {
            ItemEntity.RootEntity.TYPE_CATEGORY -> Root.CategoryRoot(
                category = getCategory(entity.root.categoryId!!)
            )

            ItemEntity.RootEntity.TYPE_PARENT_ITEM -> Root.ParentRoot(
                parentItem = getParentItem(entity.root.parentItemId!!)
            )

            else -> throw RuntimeException("Invalid root type")
        }

        return Item(
            id = ItemId(entity.id),
            userId = UserId(entity.userId),
            name = entity.name,
            description = entity.description,
            root = root,
            brand = entity.brand,
            barcode = entity.barcode,
            photo = entity.photo?.toDomain(),
            stock = entity.stock.toDomain(),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            removedAt = entity.removedAt,
            version = entity.version,
        )
    }

    private fun ItemEntity.ItemPhotoEntity.toDomain() =
        pl.przemyslawpitus.inventory.inventory.domain.item.ItemPhoto(
            id = pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId(this.id),
            url = this.url,
        )

    private fun ItemEntity.StockEntity.toDomain() =
        Stock(
            currentStock = this.currentStock,
            desiredStock = this.desiredStock,
            stockHistory = this.stockHistory.map {
                StockHistoryEntry(
                    change = it.change,
                    stockAfterChange = it.stockAfterChange,
                    createdAt = it.createdAt,
                )
            }
        )

    private fun getCategory(categoryId: String): Category =
        categoryRepository.getById(CategoryId(categoryId))
            ?: throw RuntimeException("Category $categoryId not found")

    private fun getParentItem(parentItemId: String): ParentItem =
        parentItemRepository.getById(ParentItemId(parentItemId))
            ?: throw RuntimeException("Parent item $parentItemId not found")
}