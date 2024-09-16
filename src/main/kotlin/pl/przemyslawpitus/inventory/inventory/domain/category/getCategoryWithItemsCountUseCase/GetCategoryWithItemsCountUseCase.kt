package pl.przemyslawpitus.inventory.inventory.domain.category.getCategoryWithItemsCountUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryProvider
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemRepository

class GetCategoryWithItemsCountUseCase(
    private val categoryProvider: CategoryProvider,
    private val itemRepository: ItemRepository,
) {
    fun getCategory(categoryId: CategoryId, userId: UserId): CategoryWithItemsCount {
        val category = categoryProvider.getByIdForUser(
            categoryId = categoryId,
            userId = userId,
        )

        val itemsCount = itemRepository.countByCategoryId(categoryId)

        return CategoryWithItemsCount(
            id = category.id,
            name = category.name,
            itemsCount = itemsCount,
        )
    }
}

data class CategoryWithItemsCount(
    val id: CategoryId,
    val name: String,
    val itemsCount: Long,
)