package pl.przemyslawpitus.inventory.inventory.api.category

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryNotFound
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryValidationException
import pl.przemyslawpitus.inventory.inventory.domain.category.editCategoryUseCase.EditCategoryParameters
import pl.przemyslawpitus.inventory.inventory.domain.category.editCategoryUseCase.EditCategoryUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class EditCategoryEndpoint(
    private val editCategoryUseCase: EditCategoryUseCase,
    private val errorHandler: ErrorHandler,
) {
    @PutMapping(
        "/categories/{categoryId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun editCategory(
        @PathVariable categoryId: String,
        @RequestBody request: EditCategoryRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Edit category | $categoryId")

        try {
            editCategoryUseCase.editCategory(
                editCategoryParameters = request.toEditCategoryParameters(CategoryId(categoryId)),
                userId = userDetails.id,
            )

            return ResponseEntity.noContent().build<Unit>()
        } catch (exception: CategoryNotFound) {
            return handleCategoryNotFound(exception)
        } catch (exception: CategoryDoesNotBelongToUser) {
            return handleCategoryNotFound(exception)
        } catch (exception: CategoryValidationException) {
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

    private companion object : WithLogger()
}

data class EditCategoryRequest(
    val name: String,
) {
    fun toEditCategoryParameters(categoryId: CategoryId) = EditCategoryParameters(
        id = categoryId,
        name = this.name,
    )
}
