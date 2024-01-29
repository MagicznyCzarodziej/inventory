package pl.przemyslawpitus.inventory.infrastructure

import pl.przemyslawpitus.inventory.domain.CategoryRepository
import pl.przemyslawpitus.inventory.domain.item.Category
import pl.przemyslawpitus.inventory.domain.item.CategoryId
import pl.przemyslawpitus.inventory.domain.utils.randomUuid

class InMemoryCategoryRepository: CategoryRepository {
    private val categories = mutableMapOf<CategoryId, Category>(
        toolsCategory.id to toolsCategory,
        drugsCategory.id to drugsCategory,
        hygieneCategory.id to hygieneCategory,
        cleaningCategory.id to cleaningCategory,
        kitchenCategory.id to kitchenCategory,
        foodCategory.id to foodCategory,
    )

    override fun getById(categoryId: CategoryId): Category? {
        return categories[categoryId]
    }

    override fun getAll(): List<Category> {
        return categories.values.toList();
    }
}


// TODO Remove this

val toolsCategory = Category(
    id = CategoryId(randomUuid()),
    name = "Narzędzia i elektronika",
    version = 0,
)

val drugsCategory = Category(
    id = CategoryId(randomUuid()),
    name = "Leki",
    version = 0,
)

val hygieneCategory = Category(
    id = CategoryId(randomUuid()),
    name = "Higiena",
    version = 0,
)

val cleaningCategory = Category(
    id = CategoryId(randomUuid()),
    name = "Sprzątańsko",
    version = 0,
)

val kitchenCategory = Category(
    id = CategoryId(randomUuid()),
    name = "Kuchnia",
    version = 0,
)

val foodCategory = Category(
    id = CategoryId(randomUuid()),
    name = "Jedzenie",
    version = 0,
)
