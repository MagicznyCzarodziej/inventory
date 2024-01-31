package pl.przemyslawpitus.inventory.infrastructure.mongodb

import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import pl.przemyslawpitus.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.domain.category.CategoryId

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
}


@Document("categories")
data class CategoryEntity(
    val id: String,
    val name: String,
    @Version val version: Long,
)

private fun CategoryEntity.toDomain() = Category(
    id = CategoryId(this.id),
    name = this.name,
    version = this.version,
)

private fun List<CategoryEntity>.toDomain() = this.map { it.toDomain() }

private fun Category.toEntity() = CategoryEntity(
    id = this.id.value,
    name = this.name,
    version = this.version

)