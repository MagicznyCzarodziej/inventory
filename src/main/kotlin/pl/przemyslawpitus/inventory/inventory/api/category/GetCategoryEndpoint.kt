package pl.przemyslawpitus.inventory.inventory.api.category

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryNotFound
import pl.przemyslawpitus.inventory.inventory.domain.category.getCategoryWithItemsCountUseCase.CategoryWithItemsCount
import pl.przemyslawpitus.inventory.inventory.domain.category.getCategoryWithItemsCountUseCase.GetCategoryWithItemsCountUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class GetCategoryEndpoint(
    private val getCategoryWithItemsCountUseCase: GetCategoryWithItemsCountUseCase,
    private val errorHandler: ErrorHandler,
) {
    @GetMapping(
        "/categories/{categoryId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getItem(
        @PathVariable categoryId: String,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Get category | $categoryId")

        try {
            val category = getCategoryWithItemsCountUseCase.getCategory(
                categoryId = CategoryId(categoryId),
                userId = userDetails.id,
            )

            return ResponseEntity.ok(
                category.toGetCategoryResponse()
            )
        } catch (exception: CategoryNotFound) {
            return handleCategoryNotFound(exception)
        } catch (exception: CategoryDoesNotBelongToUser) {
            return handleCategoryNotFound(exception)
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

data class GetCategoryResponse(
    val id: String,
    val name: String,
    val itemsCount: Long,
)

private fun CategoryWithItemsCount.toGetCategoryResponse() = GetCategoryResponse(
    id = id.value,
    name = name,
    itemsCount = itemsCount,
)
