package pl.przemyslawpitus.inventory.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.removeItemUseCase.RemoveItemUseCase
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
    ): ResponseEntity<*> {
        logger.api("Remove item | $itemId")

        removeItemUseCase.removeItem(
            ItemId(itemId)
        )

        return ResponseEntity.noContent().build<Unit>()
    }

    private companion object : WithLogger()
}