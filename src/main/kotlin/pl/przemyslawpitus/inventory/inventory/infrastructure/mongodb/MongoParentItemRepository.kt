package pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb

import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.common.infrastructure.mongodb.queryByUserId
import java.time.Instant

class MongoParentItemRepository(
    private val mongoTemplate: MongoTemplate,
    private val parentItemEntityToDomainMapper: ParentItemEntityToDomainMapper,
) : ParentItemRepository {
    override fun save(parentItem: ParentItem): ParentItem {
        return mongoTemplate.save(parentItem.toEntity()).toDomain()
    }

    override fun getById(parentItemId: ParentItemId): ParentItem? {
        return mongoTemplate.findById(parentItemId.value, ParentItemEntity::class.java)?.toDomain()
    }

    override fun getByUserId(userId: UserId): List<ParentItem> {
        return mongoTemplate.find(queryByUserId(userId), ParentItemEntity::class.java).toDomain()
    }

    override fun getAll(): List<ParentItem> {
        return mongoTemplate.findAll(ParentItemEntity::class.java).toDomain()
    }

    private fun ParentItemEntity.toDomain() = parentItemEntityToDomainMapper.mapToDomain(this)
    private fun List<ParentItemEntity>.toDomain() = this.map { parentItemEntityToDomainMapper.mapToDomain(it) }
}

@Document("parentItems")
data class ParentItemEntity(
    val id: String,
    val userId: String,
    val name: String,
    val categoryId: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val removedAt: Instant?,
    @Version val version: Long,
)

private fun ParentItem.toEntity() = ParentItemEntity(
    id = this.id.value,
    userId = this.userId.value,
    name = this.name,
    categoryId = this.category.id.value,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    removedAt = this.removedAt,
    version = this.version,
)