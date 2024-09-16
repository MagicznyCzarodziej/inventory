package pl.przemyslawpitus.inventory.inventory.domain.category.editCategoryUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.logging.WithLogger

class EditCategoryUseCase(
    private val categoryProvider: CategoryProvider,
    private val categoryRepository: CategoryRepository,
    private val editCategoryTransformer: EditCategoryTransformer,
) {
    fun editCategory(editCategoryParameters: EditCategoryParameters, userId: UserId): Category {
        val category = categoryProvider.getByIdForUser(
            categoryId = editCategoryParameters.id,
            userId = userId,
        )

        val updatedCategory = editCategoryTransformer.editCategory(
            category = category,
            editCategoryParameters = editCategoryParameters,
        )

        val savedCategory = categoryRepository.save(updatedCategory)
        logger.domain("Saved category | ${category.id}")

        return savedCategory
    }

    private companion object : WithLogger()
}

data class EditCategoryParameters(
    val id: CategoryId,
    val name: String,
)