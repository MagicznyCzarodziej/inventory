package pl.przemyslawpitus.inventory.inventory.api.item

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.inventory.domain.item.changeItemCountUseCase.UpdateCurrentStockUseCase
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.item.ItemNotFound
import pl.przemyslawpitus.inventory.inventory.domain.item.changeItemCountUseCase.CurrentStockCannotBeNegativeException
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class UpdateCurrentStockEndpoint(
    private val updateCurrentStockUseCase: UpdateCurrentStockUseCase,
    private val errorHandler: ErrorHandler,
) {

    @PutMapping(
        "/items/{itemId}/stock/current",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateCurrentStock(
        @RequestBody request: UpdateCurrentStockRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable itemId: String,
    ): ResponseEntity<*> {
        logger.api("Update item stock | $itemId")

        try {
            updateCurrentStockUseCase.updateCurrentStock(
                itemId = ItemId(itemId),
                stockChange = request.stockChange,
                userId = userDetails.id,
            )

            return ResponseEntity.noContent().build<Unit>()
        } catch (exception: ItemNotFound) {
            return handleItemNotFound(exception)
        } catch (exception: ItemDoesNotBelongToUser) {
            return handleItemNotFound(exception)
        } catch (exception: CurrentStockCannotBeNegativeException) {
            return errorHandler.handleError(
                code = "INVALID_REQUEST",
                status = HttpStatus.BAD_REQUEST,
                message = exception.message!!,
                exception = exception,
            )
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

data class UpdateCurrentStockRequest(
    val stockChange: Int,
)