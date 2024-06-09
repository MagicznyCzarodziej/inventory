package pl.przemyslawpitus.inventory.inventory.api.item

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.removeItemUseCase.RemoveItemUseCase
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemNotFound
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class RemoveItemEndpoint(
    private val removeItemUseCase: RemoveItemUseCase,
    private val errorHandler: ErrorHandler,
) {
    @DeleteMapping(
        "/items/{itemId}",
    )
    fun removeItem(
        @PathVariable itemId: String,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Remove item | $itemId")

        try {
            removeItemUseCase.removeItem(
                itemId = ItemId(itemId),
                userId = userDetails.id,
            )

            return ResponseEntity.noContent().build<Unit>()
        } catch (exception: ItemNotFound) {
            return handleItemNotFound(exception)
        } catch (exception: ItemDoesNotBelongToUser) {
            return handleItemNotFound(exception)
        }
    }

    private fun handleItemNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "ITEM_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "Item not found",
            exception = exception,
        )

    private companion object : WithLogger()
}