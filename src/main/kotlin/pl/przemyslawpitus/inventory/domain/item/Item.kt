package pl.przemyslawpitus.inventory.domain.item

import pl.przemyslawpitus.inventory.domain.user.UserId
import pl.przemyslawpitus.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.domain.parentItem.ParentItem
import java.time.Instant

data class ItemId(val value: String)

data class Item(
    val id: ItemId,
    val userId: UserId,
    val name: String,
    val description: String?,
    val root: Root,
    val brand: String?,
    val barcode: String?,
    val photo: ItemPhoto?,
    val stock: Stock,
    val createdAt: Instant,
    val updatedAt: Instant,
    val removedAt: Instant?,
    val version: Long,
)

sealed interface Root {
    data class ParentRoot(val parentItem: ParentItem) : Root
    data class CategoryRoot(val category: Category) : Root
}