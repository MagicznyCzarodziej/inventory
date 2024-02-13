package pl.przemyslawpitus.inventory.api.item

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.item.removeItemUseCase.RemoveItemUseCase
import pl.przemyslawpitus.inventory.domain.user.UserDetails
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class RemoveItemEndpoint(
    private val removeItemUseCase: RemoveItemUseCase,
) {
    @DeleteMapping(
        "/items/{itemId}",
    )
    fun removeItem(
        @PathVariable itemId: String,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Remove item | $itemId")

        removeItemUseCase.removeItem(
            itemId = ItemId(itemId),
            userId = userDetails.id,
        )

        return ResponseEntity.noContent().build<Unit>()
    }

    private companion object : WithLogger()
}