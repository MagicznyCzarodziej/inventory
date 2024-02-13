package pl.przemyslawpitus.inventory.api.item

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.item.editItemUseCase.EditItemParameters
import pl.przemyslawpitus.inventory.domain.item.editItemUseCase.EditItemUseCase
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.user.UserDetails
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class EditItemEndpoint(
    private val editItemUseCase: EditItemUseCase,
) {
    @PutMapping(
        "/items/{itemId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun createItem(
        @PathVariable itemId: String,
        @RequestBody request: EditItemRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Create item | ${request.name}")

        editItemUseCase.editItem(
            editItemParameters = request.toEditItemParameters(ItemId(itemId)),
            userId = userDetails.id,
        )

        return ResponseEntity.noContent().build<Unit>()
    }

    private companion object : WithLogger()
}

data class  EditItemRequest(
    val name: String,
    val description: String?,
    val brand: String?,
    val desiredStock: Int,
) {
    fun toEditItemParameters(itemId: ItemId) = EditItemParameters(
        id = itemId,
        name = this.name,
        description = this.description,
        brand = this.brand,
        desiredStock = this.desiredStock,
    )
}
