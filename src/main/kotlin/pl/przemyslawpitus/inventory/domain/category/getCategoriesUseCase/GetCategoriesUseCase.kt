package pl.przemyslawpitus.inventory.domain.category.getCategoriesUseCase

import pl.przemyslawpitus.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.domain.user.UserId

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {
    fun getCategories(userId: UserId): List<Category> {
        return categoryRepository.getByUserId(userId)
    }
}