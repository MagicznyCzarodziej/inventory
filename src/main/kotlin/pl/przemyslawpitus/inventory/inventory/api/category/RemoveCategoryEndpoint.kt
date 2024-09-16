package pl.przemyslawpitus.inventory.inventory.api.category

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryNotFound
import pl.przemyslawpitus.inventory.inventory.domain.category.removeCategoryUseCase.CannotDeleteCategoryWithItems
import pl.przemyslawpitus.inventory.inventory.domain.category.removeCategoryUseCase.RemoveCategoryUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class RemoveCategoryEndpoint(
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val errorHandler: ErrorHandler,
) {
    @DeleteMapping(
        "/categories/{categoryId}",
    )
    fun removeCategory(
        @PathVariable categoryId: String,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Remove category | $categoryId")

        try {
            removeCategoryUseCase.removeCategory(
                categoryId = CategoryId(categoryId),
                userId = userDetails.id,
            )

            return ResponseEntity.noContent().build<Unit>()
        } catch (exception: CategoryNotFound) {
            return handleCategoryNotFound(exception)
        } catch (exception: CategoryDoesNotBelongToUser) {
            return handleCategoryNotFound(exception)
        } catch (exception: CannotDeleteCategoryWithItems) {
            return errorHandler.handleError(
                code = "CANNOT_DELETE_CATEGORY_WITH_ITEMS",
                status = HttpStatus.UNPROCESSABLE_ENTITY,
                message = "Cannot delete category with items",
                exception = exception,
            )
        }
    }

    private fun handleCategoryNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "ITEM_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "Category not found",
            exception = exception,
        )

    private companion object : WithLogger()
}