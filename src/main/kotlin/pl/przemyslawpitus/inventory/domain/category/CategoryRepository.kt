package pl.przemyslawpitus.inventory.domain.category

interface CategoryRepository {
    fun getById(categoryId: CategoryId): Category?
    fun getAll(): List<Category>
    fun save(category: Category): Category
}