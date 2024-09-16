package pl.przemyslawpitus.inventory.inventory.domain.category.createCategoryUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.common.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryValidations
import pl.przemyslawpitus.inventory.logging.WithLogger

class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository,
) {
    fun createCategory(categoryName: String, userId: UserId): Category {
        logger.domain("Create category | $categoryName")

        CategoryValidations.validateName(categoryName)

        val category = Category(
            id = CategoryId(randomUuid()),
            userId = userId,
            name = categoryName,
            version = 0,
        )

        val savedCategory = categoryRepository.save(category)

        logger.domain("Saved category | ${savedCategory.id}")

        return savedCategory
    }

    private companion object : WithLogger()
}