package pl.przemyslawpitus.inventory.domain

import pl.przemyslawpitus.inventory.domain.item.Category
import pl.przemyslawpitus.inventory.domain.item.CategoryId

interface CategoryRepository {
    fun getById(categoryId: CategoryId): Category?
    fun getAll(): List<Category>
}