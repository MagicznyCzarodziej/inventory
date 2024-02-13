package pl.przemyslawpitus.inventory.domain.category

import pl.przemyslawpitus.inventory.domain.user.UserId

interface CategoryRepository {
    fun getById(categoryId: CategoryId): Category?
    fun getAll(): List<Category>
    fun save(category: Category): Category
    fun getByUserId(userId: UserId): List<Category>
}