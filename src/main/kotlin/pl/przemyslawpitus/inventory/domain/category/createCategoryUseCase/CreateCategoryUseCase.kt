package pl.przemyslawpitus.inventory.domain.category.createCategoryUseCase

import pl.przemyslawpitus.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger

class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository,
) {
    fun createCategory(categoryName: String): Category {
        logger.domain("Create category | $categoryName")

        val category = Category(
            id = CategoryId(randomUuid()),
            name = categoryName,
            version = 0,
        )

        val savedCategory = categoryRepository.save(category)

        logger.domain("Saved category | ${savedCategory.id}")

        return savedCategory
    }

    private companion object : WithLogger()
}