package pl.przemyslawpitus.inventory.infrastructure.mongodb

import pl.przemyslawpitus.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItemId

class ParentItemEntityToDomainMapper(
    private val categoryRepository: CategoryRepository,
) {
    fun mapToDomain(parentItemEntity: ParentItemEntity) =
        ParentItem(
            id = ParentItemId(value = parentItemEntity.id),
            name = parentItemEntity.name,
            category = getCategory(parentItemEntity.categoryId),
            createdAt = parentItemEntity.createdAt,
            updatedAt = parentItemEntity.updatedAt,
            removedAt = parentItemEntity.removedAt,
            version = parentItemEntity.version
        )

    private fun getCategory(categoryId: String): Category =
        categoryRepository.getById(CategoryId(categoryId))
            ?: throw RuntimeException("Category $categoryId not found")
}