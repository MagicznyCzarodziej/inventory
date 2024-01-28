package pl.przemyslawpitus.inventory.infrastructure

import pl.przemyslawpitus.inventory.domain.CategoryRepository
import pl.przemyslawpitus.inventory.domain.item.Category
import pl.przemyslawpitus.inventory.domain.item.CategoryId

class InMemoryCategoryRepository: CategoryRepository {
    private val categories = mutableMapOf<CategoryId, Category>(
        foodCategory.id to foodCategory,
        cleaningCategory.id to cleaningCategory,
    )

    override fun getById(categoryId: CategoryId): Category? {
        return categories[categoryId]
    }
}

// TODO Remove this
val foodCategory = Category(
    id = CategoryId(value = "category-food"),
    name = "Jedzenie",
    version = 0,
)

val cleaningCategory = Category(
    id = CategoryId(value = "category-cleaning"),
    name = "Sprzątańsko",
    version = 0,
)