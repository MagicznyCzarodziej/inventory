package pl.przemyslawpitus.inventory.inventory.api.item

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.inventory.domain.item.getItemsUseCase.GetItemsUseCase
import pl.przemyslawpitus.inventory.inventory.domain.item.getItemsUseCase.ItemsView
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.item.Item
import pl.przemyslawpitus.inventory.inventory.domain.item.Root
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class GetItemsEndpoint(
    private val getItemsUseCase: GetItemsUseCase,
) {
    @GetMapping(
        "/items",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getItems(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Get items")

        val items = getItemsUseCase.getItems(
            userId = userDetails.id,
        )

        return ResponseEntity.ok(
            items.toGetItemsResponse()
        )
    }

    private companion object : WithLogger()
}

data class GetItemsResponse(
    val entries: List<Entry>,
) {
    sealed class Entry {
        data class ParentEntry(
            val id: String,
            val name: String,
            val items: List<ItemEntry>,
        ) : Entry()

        data class ItemEntry(
            val id: String,
            val name: String,
            val brand: String?,
            val category: Category,
            val currentStock: Int,
            val desiredStock: Int,
        ) : Entry()
    }

    data class Category(
        val id: String,
        val name: String,
    )
}

private fun List<ItemsView.Entry>.toGetItemsResponse() = GetItemsResponse(
    entries = this.map {
        when (it) {
            is ItemsView.Entry.ParentEntry -> GetItemsResponse.Entry.ParentEntry(
                id = it.parentItem.id.value,
                name = it.parentItem.name,
                items = it.items.map { item -> item.toGetItemsResponse()},
            )

            is ItemsView.Entry.IndependentItemEntry -> it.item.toGetItemsResponse()
        }
    }
)

private fun Item.toGetItemsResponse(): GetItemsResponse.Entry.ItemEntry {
    val category = when (this.root) {
        is Root.CategoryRoot -> GetItemsResponse.Category(
            id = this.root.category.id.value,
            name = this.root.category.name,
        )

        is Root.ParentRoot -> GetItemsResponse.Category(
            id = this.root.parentItem.category.id.value,
            name = this.root.parentItem.category.name,
        )
    }

    return GetItemsResponse.Entry.ItemEntry(
        id = this.id.value,
        name = this.name,
        brand = this.brand,
        category = category,
        currentStock = this.stock.currentStock,
        desiredStock = this.stock.desiredStock,
    )
}
