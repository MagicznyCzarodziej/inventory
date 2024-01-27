package pl.przemyslawpitus.inventory.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.item.ItemId
import pl.przemyslawpitus.inventory.domain.changeItemCountUseCase.UpdateCurrentStockUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class UpdateCurrentStockEndpoint(
    private val updateCurrentStockUseCase: UpdateCurrentStockUseCase,
) {

    @PutMapping(
        "/item/{itemId}/stock/current",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateCurrentStock(
        @RequestBody request: UpdateCurrentStockRequest,
        @PathVariable itemId: String,
    ): ResponseEntity<*> {
        logger.api("Update item stock | $itemId")

        updateCurrentStockUseCase.updateCurrentStock(
            itemId = ItemId(itemId),
            stockChange = request.stockChange,
        )

        return ResponseEntity.noContent().build<Unit>()
    }

    private companion object : WithLogger()
}

data class UpdateCurrentStockRequest(
    val stockChange: Int,
)