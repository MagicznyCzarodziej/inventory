package pl.przemyslawpitus.inventory.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.getParentItemsUseCase.GetParentItemsUseCase
import pl.przemyslawpitus.inventory.domain.item.ParentItem
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class GetParentItemsEndpoint(
    private val getParentItemsUseCase: GetParentItemsUseCase,
) {
    @GetMapping(
        "/parent-items",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getParentItems(): ResponseEntity<*> {
        logger.api("Get parent items")

        val parentItems = getParentItemsUseCase.getParentItems()

        return ResponseEntity.ok(
            parentItems.toGetParentItemsResponse()
        )
    }

    private companion object : WithLogger()
}

data class GetParentItemsResponse(
    val parentItems: List<ParentItemDto>,
) {
    data class ParentItemDto(
        val id: String,
        val name: String,
    )
}

fun List<ParentItem>.toGetParentItemsResponse() = GetParentItemsResponse(
    parentItems = this.map {
        GetParentItemsResponse.ParentItemDto(
            id = it.id.value,
            name = it.name,
        )
    }
)