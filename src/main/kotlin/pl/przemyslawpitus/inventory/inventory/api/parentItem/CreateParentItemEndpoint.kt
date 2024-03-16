package pl.przemyslawpitus.inventory.inventory.api.parentItem

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.createParentItemUseCase.CreateParentItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.createParentItemUseCase.ParentItemDraft
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class CreateParentItemEndpoint(
    private val createParentItemUseCase: CreateParentItemUseCase,
) {
    @PostMapping(
        "/parent-items",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createItem(
        @RequestBody request: CreateParentItemRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Create parent item | ${request.name}")

        val item = createParentItemUseCase.createParentItem(
            itemDraft = request.toParentItemDraft(),
            userId = userDetails.id,
        )

        return ResponseEntity.ok(
            item.toCreateParentItemResponse()
        )
    }

    private companion object : WithLogger()
}

data class CreateParentItemRequest(
    val name: String,
    val categoryId: String,
) {
    fun toParentItemDraft() = ParentItemDraft(
        name = name,
        categoryId = CategoryId(categoryId),
    )
}

data class CreateParentItemResponse(
    val id: String,
    val name: String,
)

private fun ParentItem.toCreateParentItemResponse() =
    CreateParentItemResponse(
        id = this.id.value,
        name = this.name
    )