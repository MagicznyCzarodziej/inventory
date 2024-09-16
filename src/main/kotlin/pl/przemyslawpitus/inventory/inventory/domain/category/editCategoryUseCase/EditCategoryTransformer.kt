package pl.przemyslawpitus.inventory.inventory.domain.category.editCategoryUseCase

import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryValidations
import pl.przemyslawpitus.inventory.logging.WithLogger

class EditCategoryTransformer {
    fun editCategory(category: Category, editCategoryParameters: EditCategoryParameters): Category {
        if (!hasAnyFieldChanged(category, editCategoryParameters)) {
            logger.domain("Nothing changed | ${category.id}")
            return category
        }

        CategoryValidations.validateName(editCategoryParameters.name)

        return category.copy(
            name = editCategoryParameters.name.trim(),
        )
    }

    private fun hasAnyFieldChanged(
        currentCategory: Category,
        editCategoryParameters: EditCategoryParameters
    ): Boolean = currentCategory.name != editCategoryParameters.name

    private companion object : WithLogger()
}