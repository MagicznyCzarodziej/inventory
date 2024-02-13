package pl.przemyslawpitus.inventory.infrastructure.mongodb

import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import pl.przemyslawpitus.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.domain.user.UserId
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class MongoItemRepository(
    private val mongoTemplate: MongoTemplate,
    private val itemEntityToDomainMapper: ItemEntityToDomainMapper,
) : ItemRepository {
    override fun getById(itemId: ItemId): Item? {
        logger.infra("Get item | $itemId")
        return mongoTemplate.findById(itemId.value, ItemEntity::class.java)?.toDomain()
    }

    override fun getByUserId(userId: UserId): List<Item> {
        logger.infra("Get items for user | $userId")
        return mongoTemplate.find(queryByUserId(userId), ItemEntity::class.java).toDomain()
    }

    override fun getAll(): List<Item> {
        logger.infra("Get all items")
        return mongoTemplate.findAll(ItemEntity::class.java).toDomain()
    }

    override fun save(item: Item): Item {
        logger.infra("Save item | ${item.id}")
        return mongoTemplate.save(item.toEntity()).toDomain()
    }

    override fun removeById(itemId: ItemId) {
        logger.infra("Remove item | $itemId")
        mongoTemplate.remove(queryById(itemId.value), ItemEntity::class.java)
    }

    private fun ItemEntity.toDomain() = itemEntityToDomainMapper.mapToDomain(this)
    private fun List<ItemEntity>.toDomain() = this.map { itemEntityToDomainMapper.mapToDomain(it) }

    private fun queryById(id: String) = Query().addCriteria(Criteria.where("_id").isEqualTo(id))

    private companion object : WithLogger()
}

@Document("items")
data class ItemEntity(
    val id: String,
    val userId: String,
    val name: String,
    val description: String?,
    val root: RootEntity,
    val brand: String?,
    val barcode: String?,
    val photo: ItemPhotoEntity?,
    val stock: StockEntity,
    val createdAt: Instant,
    val updatedAt: Instant,
    val removedAt: Instant?,
    @Version val version: Long,
) {
    data class RootEntity(
        val type: String,
        val categoryId: String?,
        val parentItemId: String?,
    ) {
        companion object {
            const val TYPE_CATEGORY = "CATEGORY"
            const val TYPE_PARENT_ITEM = "PARENT_ITEM"
        }
    }

    data class ItemPhotoEntity(
        val id: String,
        val url: String,
    )

    data class StockEntity(
        val currentStock: Int,
        val desiredStock: Int,
        val stockHistory: List<StockHistoryEntryEntity>,
    )

    data class StockHistoryEntryEntity(
        val change: Int,
        val stockAfterChange: Int,
        val createdAt: Instant,
    )
}

private fun Item.toEntity() = ItemEntity(
    id = this.id.value,
    userId = this.userId.value,
    name = this.name,
    description = this.description,
    root = when (this.root) {
        is Root.CategoryRoot -> ItemEntity.RootEntity(
            type = ItemEntity.RootEntity.TYPE_CATEGORY,
            categoryId = this.root.category.id.value,
            parentItemId = null,
        )

        is Root.ParentRoot -> ItemEntity.RootEntity(
            type = ItemEntity.RootEntity.TYPE_PARENT_ITEM,
            categoryId = null,
            parentItemId = this.root.parentItem.id.value,
        )
    },
    brand = this.brand,
    barcode = this.barcode,
    photo = this.photo?.let {
        ItemEntity.ItemPhotoEntity(
            id = this.photo.id.value,
            url = this.photo.url,
        )
    },
    stock = ItemEntity.StockEntity(
        currentStock = this.stock.currentStock,
        desiredStock = this.stock.desiredStock,
        stockHistory = this.stock.stockHistory.map {
            ItemEntity.StockHistoryEntryEntity(
                change = it.change,
                stockAfterChange = it.stockAfterChange,
                createdAt = it.createdAt,
            )
        }
    ),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    removedAt = this.removedAt,
    version = this.version,
)
