package pl.przemyslawpitus.inventory.domain.category

import pl.przemyslawpitus.inventory.domain.user.UserId

data class Category(
    val id: CategoryId,
    val userId: UserId,
    val name: String,
    val version: Long = 0,
)

data class CategoryId(val value: String) {
    companion object {
        fun orNull(value: String?) = value?.let { CategoryId(value = it) }
    }
}

