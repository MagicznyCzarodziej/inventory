package pl.przemyslawpitus.inventory.inventory.api.parentItem

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.removeParentItemUseCase.RemoveParentItemUseCase
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemNotFound
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.removeParentItemUseCase.CannotDeleteParentItemWithSubItems
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class RemoveParentItemEndpoint(
    private val removeParentItemUseCase: RemoveParentItemUseCase,
    private val errorHandler: ErrorHandler,
) {
    @DeleteMapping(
        "/parent-items/{parentItemId}",
    )
    fun removeParentItem(
        @PathVariable parentItemId: String,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Remove parent item | $parentItemId")

        try {
            removeParentItemUseCase.removeParentItem(
                parentItemId = ParentItemId(parentItemId),
                userId = userDetails.id,
            )

            return ResponseEntity.noContent().build<Unit>()
        } catch (exception: ParentItemNotFound) {
            return handleParentItemNotFound(exception)
        } catch (exception: ParentItemDoesNotBelongToUser) {
            return handleParentItemNotFound(exception)
        } catch (exception: CannotDeleteParentItemWithSubItems) {
            return errorHandler.handleError(
                code = "CANNOT_DELETE_PARENT_ITEM_WITH_SUB_ITEMS",
                status = HttpStatus.UNPROCESSABLE_ENTITY,
                message = "Cannot delete parent item with sub items",
                exception = exception,
            )
        }
    }

    private fun handleParentItemNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "ITEM_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "ParentItem not found",
            exception = exception,
        )

    private companion object : WithLogger()
}