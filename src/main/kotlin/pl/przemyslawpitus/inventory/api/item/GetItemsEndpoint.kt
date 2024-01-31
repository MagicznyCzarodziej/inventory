package pl.przemyslawpitus.inventory.api.item

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.item.getItemsUseCase.GetItemsUseCase
import pl.przemyslawpitus.inventory.domain.item.getItemsUseCase.ItemsView
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class GetItemsEndpoint(
    private val getItemsUseCase: GetItemsUseCase,
) {
    @GetMapping(
        "/items",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getItems(): ResponseEntity<*> {
        logger.api("Get items")

        val items = getItemsUseCase.getItems()

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
            val currentStock: Int,
            val desiredStock: Int,
        ) : Entry()
    }
}

private fun List<ItemsView.Entry>.toGetItemsResponse() = GetItemsResponse(
    entries = this.map {
        when (it) {
            is ItemsView.Entry.ParentEntry -> GetItemsResponse.Entry.ParentEntry(
                id = it.parentItem.id.value,
                name = it.parentItem.name,
                items = it.items.map { item ->
                    GetItemsResponse.Entry.ItemEntry(
                        id = item.id.value,
                        name = item.name,
                        brand = item.brand,
                        currentStock = item.stock.currentStock,
                        desiredStock = item.stock.desiredStock,
                    )
                }
            )

            is ItemsView.Entry.IndependentItemEntry -> GetItemsResponse.Entry.ItemEntry(
                id = it.item.id.value,
                name = it.item.name,
                brand = it.item.brand,
                currentStock = it.item.stock.currentStock,
                desiredStock = it.item.stock.desiredStock,
            )
        }
    }
)