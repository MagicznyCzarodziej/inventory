package pl.przemyslawpitus.inventory.inventory.infrastructure.mongodb

import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.common.infrastructure.mongodb.queryByUserId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId

class MongoCategoryRepository(
    private val mongoTemplate: MongoTemplate,
) : CategoryRepository {
    override fun getById(categoryId: CategoryId): Category? {
        return mongoTemplate.findById(categoryId.value, CategoryEntity::class.java)?.toDomain()
    }

    override fun getAll(): List<Category> {
        return mongoTemplate.findAll(CategoryEntity::class.java).toDomain()
    }

    override fun save(category: Category): Category {
        return mongoTemplate.save(category.toEntity()).toDomain()
    }

    override fun getByUserId(userId: UserId): List<Category> {
        return mongoTemplate.find(queryByUserId(userId), CategoryEntity::class.java).toDomain()
    }
}


@Document("categories")
data class CategoryEntity(
    val id: String,
    val userId: String,
    val name: String,
    @Version val version: Long,
)

private fun CategoryEntity.toDomain() = Category(
    id = CategoryId(this.id),
    userId = UserId(this.userId),
    name = this.name,
    version = this.version,
)

private fun List<CategoryEntity>.toDomain() = this.map { it.toDomain() }

private fun Category.toEntity() = CategoryEntity(
    id = this.id.value,
    userId = this.userId.value,
    name = this.name,
    version = this.version
)