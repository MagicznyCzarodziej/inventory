package pl.przemyslawpitus.inventory.inventory.api.parentItem

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.editParentItemUseCase.EditParentItemParameters
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.editParentItemUseCase.EditParentItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryNotFound
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemNotFound
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemValidationException
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class EditParentItemEndpoint(
    private val editParentItemUseCase: EditParentItemUseCase,
    private val errorHandler: ErrorHandler,
) {
    @PutMapping(
        "/parent-items/{parentItemId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun editParentItem(
        @PathVariable parentItemId: String,
        @RequestBody request: EditParentItemRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Edit parentItem | $parentItemId")

        try {
            editParentItemUseCase.editParentItem(
                editParentItemParameters = request.toEditParentItemParameters(ParentItemId(parentItemId)),
                userId = userDetails.id,
            )

            return ResponseEntity.noContent().build<Unit>()
        } catch (exception: ParentItemNotFound) {
            return handleParentItemNotFound(exception)
        } catch (exception: ParentItemDoesNotBelongToUser) {
            return handleParentItemNotFound(exception)
        } catch (exception: CategoryNotFound) {
            return handleCategoryNotFound(exception)
        } catch (exception: CategoryDoesNotBelongToUser) {
            return handleCategoryNotFound(exception)
        } catch (exception: ParentItemValidationException) {
            return errorHandler.handleError(
                code = "INVALID_REQUEST",
                status = HttpStatus.BAD_REQUEST,
                message = exception.message!!,
                exception = exception,
            )
        }
    }

    private fun handleCategoryNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "CATEGORY_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "Category not found",
            exception = exception,
        )

    private fun handleParentItemNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "ITEM_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "ParentItem not found",
            exception = exception,
        )

    private companion object : WithLogger()
}

data class EditParentItemRequest(
    val name: String,
    val categoryId: String,
) {
    fun toEditParentItemParameters(parentItemId: ParentItemId) = EditParentItemParameters(
        id = parentItemId,
        name = this.name,
        categoryId = CategoryId(this.categoryId),
    )
}
