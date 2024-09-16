package pl.przemyslawpitus.inventory.inventory.domain.category

import pl.przemyslawpitus.inventory.common.domain.user.UserId

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

object CategoryValidations {
    fun validateName(name: String) {
        if (name.isBlank()) {
            throw CategoryValidationException("Name cannot be blank")
        }
    }
}

class CategoryValidationException(message: String) : RuntimeException(message)