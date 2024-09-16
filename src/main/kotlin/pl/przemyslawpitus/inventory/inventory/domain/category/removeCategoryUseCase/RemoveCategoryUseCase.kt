package pl.przemyslawpitus.inventory.inventory.domain.category.removeCategoryUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryRepository
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository
import pl.przemyslawpitus.inventory.logging.WithLogger

class RemoveCategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val categoryProvider: CategoryProvider,
    private val itemRepository: ItemRepository,
    ) {
    fun removeCategory(categoryId: CategoryId, userId: UserId) {
        // Check if category exists and belongs to user
        categoryProvider.getByIdForUser(
            categoryId = categoryId,
            userId = userId,
        )

        val itemsCount = itemRepository.countByCategoryId(categoryId)
        if (itemsCount > 0) {
            throw CannotDeleteCategoryWithItems(categoryId)
        }

        categoryRepository.removeById(categoryId)
        logger.domain("Removed category | $categoryId")
    }

    private companion object : WithLogger()
}

class CannotDeleteCategoryWithItems(categoryId: CategoryId) :
    RuntimeException("Cannot delete category $categoryId, because it has items")
