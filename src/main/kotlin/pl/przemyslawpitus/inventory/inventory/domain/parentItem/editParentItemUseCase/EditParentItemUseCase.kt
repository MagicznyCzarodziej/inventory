package pl.przemyslawpitus.inventory.inventory.domain.parentItem.editParentItemUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemProvider
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemRepository
import pl.przemyslawpitus.inventory.logging.WithLogger

class EditParentItemUseCase(
    private val parentItemProvider: ParentItemProvider,
    private val editParentItemTransformer: EditParentItemTransformer,
    private val parentItemRepository: ParentItemRepository,
) {
    fun editParentItem(editParentItemParameters: EditParentItemParameters, userId: UserId): ParentItem {
        logger.domain("Edit parent item | ${editParentItemParameters.name}")

        val parentItem = parentItemProvider.getByIdForUser(
            parentItemId = editParentItemParameters.id,
            userId = userId,
        )

        val updatedParentItem = editParentItemTransformer.editParentItem(
            parentItem = parentItem,
            editParentItemParameters = editParentItemParameters,
            userId = userId,
        )

        val savedParentItem = parentItemRepository.save(updatedParentItem)
        logger.domain("Saved parent item | ${parentItem.id}")

        return savedParentItem
    }

    private companion object : WithLogger()
}

data class EditParentItemParameters(
    val id: ParentItemId,
    val name: String,
    val categoryId: CategoryId,
)
