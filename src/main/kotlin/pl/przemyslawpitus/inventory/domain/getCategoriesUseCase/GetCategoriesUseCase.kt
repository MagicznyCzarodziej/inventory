package pl.przemyslawpitus.inventory.domain.getCategoriesUseCase

import pl.przemyslawpitus.inventory.domain.CategoryRepository
import pl.przemyslawpitus.inventory.domain.item.Category

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {
    fun getCategories(): List<Category> {
        return categoryRepository.getAll();
    }
}