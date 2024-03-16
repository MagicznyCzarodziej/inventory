package pl.przemyslawpitus.inventory.inventory.domain.category.getCategoriesUseCase

import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.common.domain.user.UserId

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {
    fun getCategories(userId: UserId): List<Category> {
        return categoryRepository.getByUserId(userId)
    }
}