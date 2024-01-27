package pl.przemyslawpitus.inventory.domain.item

import java.time.Instant

data class ItemId(val value: String)

data class Item(
    val id: ItemId,
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