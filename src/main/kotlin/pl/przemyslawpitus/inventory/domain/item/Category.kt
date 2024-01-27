package pl.przemyslawpitus.inventory.domain.item

data class Category(
    val id: CategoryId,
    val name: String,
    val version: Long = 0,
)

data class CategoryId(val value: String) {
    companion object {
        fun orNull(value: String?) = value?.let { CategoryId(value = it) }
    }
}

