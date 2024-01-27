package pl.przemyslawpitus.inventory.infrastructure

import pl.przemyslawpitus.inventory.domain.CategoryRepository
import pl.przemyslawpitus.inventory.domain.item.Category
import pl.przemyslawpitus.inventory.domain.item.CategoryId

class InMemoryCategoryRepository: CategoryRepository {
    private val categories = mutableMapOf<CategoryId, Category>(kitchenCategory.id to kitchenCategory)

    override fun getById(categoryId: CategoryId): Category? {
        return categories[categoryId]
    }
}

// TODO Remove this
private val kitchenCategory = Category(
    id = CategoryId(value = "category-kitchen"),
    name = "Kitchen",
    version = 0,
)