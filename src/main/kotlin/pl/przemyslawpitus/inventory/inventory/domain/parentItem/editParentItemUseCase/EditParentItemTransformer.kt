package pl.przemyslawpitus.inventory.inventory.domain.parentItem.editParentItemUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemValidations
import pl.przemyslawpitus.inventory.logging.WithLogger

class EditParentItemTransformer(
    private val categoryProvider: CategoryProvider,
) {
    fun editParentItem(
        parentItem: ParentItem,
        editParentItemParameters: EditParentItemParameters,
        userId: UserId,
    ): ParentItem {
        if (!hasAnyFieldChanged(parentItem, editParentItemParameters)) {
            logger.domain("Nothing changed | ${parentItem.id}")
            return parentItem
        }

        ParentItemValidations.validateName(editParentItemParameters.name)

        return parentItem.copy(
            name = editParentItemParameters.name.trim(),
            category = updateCategory(editParentItemParameters, userId),
        )
    }

    private fun updateCategory(
        editParentItemParameters: EditParentItemParameters,
        userId: UserId,
    ): Category {
        val category = categoryProvider.getByIdForUser(
            categoryId = editParentItemParameters.categoryId,
            userId = userId
        )

        return category
    }

    private fun hasAnyFieldChanged(
        currentItem: ParentItem,
        editParentItemParameters: EditParentItemParameters,
    ): Boolean = (currentItem.name != editParentItemParameters.name
            || currentItem.category.id != editParentItemParameters.categoryId)

    private companion object : WithLogger()
}