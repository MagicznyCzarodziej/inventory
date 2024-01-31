package pl.przemyslawpitus.inventory.domain.category.getCategoriesUseCase

import pl.przemyslawpitus.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.domain.category.Category

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {
    fun getCategories(): List<Category> {
        return categoryRepository.getAll();
    }
}