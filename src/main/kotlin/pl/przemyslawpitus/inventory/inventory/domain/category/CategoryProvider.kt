package pl.przemyslawpitus.inventory.inventory.domain.category

import pl.przemyslawpitus.inventory.common.domain.user.UserId

class CategoryProvider(
    private val categoryRepository: CategoryRepository,
) {
    fun getByIdForUser(categoryId: CategoryId, userId: UserId): Category {
        val category = categoryRepository.getById(categoryId)
            ?: throw CategoryNotFound(categoryId)

        if (category.userId != userId) {
            throw CategoryDoesNotBelongToUser(
                categoryId = category.id,
                userId = userId
            )
        }

        return category
    }
}

class CategoryNotFound(categoryId: CategoryId) : RuntimeException("Category not found $categoryId")

class CategoryDoesNotBelongToUser(categoryId: CategoryId, userId: UserId) :
    RuntimeException("Category ${categoryId.value} doesn't belong to user ${userId.value}")
