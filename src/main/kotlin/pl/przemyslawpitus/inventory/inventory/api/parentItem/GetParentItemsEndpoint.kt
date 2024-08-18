package pl.przemyslawpitus.inventory.inventory.api.parentItem

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemsUseCase.GetParentItemsUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class GetParentItemsEndpoint(
    private val getParentItemsUseCase: GetParentItemsUseCase,
) {
    @GetMapping(
        "/parent-items",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getParentItems(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Get parent items")

        val parentItems = getParentItemsUseCase.getParentItems(userId = userDetails.id)

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
        val category: CategoryDto,
    )

    data class CategoryDto(
        val id: String,
        val name: String,
    )
}

fun List<ParentItem>.toGetParentItemsResponse() = GetParentItemsResponse(
    parentItems = this.map {
        GetParentItemsResponse.ParentItemDto(
            id = it.id.value,
            name = it.name,
            category = GetParentItemsResponse.CategoryDto(
                id = it.category.id.value,
                name = it.category.name,
            )
        )
    }
)