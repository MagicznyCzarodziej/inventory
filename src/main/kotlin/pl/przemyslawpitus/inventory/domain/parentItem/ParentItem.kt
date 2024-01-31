package pl.przemyslawpitus.inventory.domain.parentItem

import pl.przemyslawpitus.inventory.domain.category.Category
import java.time.Instant

data class ParentItem(
    val id: ParentItemId,
    val name: String,
    val category: Category,
    val createdAt: Instant,
    val updatedAt: Instant,
    val removedAt: Instant?,
    val version: Long,
)

data class ParentItemId(val value: String) {
    companion object {
        fun orNull(value: String?) = value?.let { ParentItemId(value = it) }
    }
}

