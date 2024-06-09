package pl.przemyslawpitus.inventory.inventory.api.parentItem

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.createParentItemUseCase.CreateParentItemUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.createParentItemUseCase.ParentItemDraft
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryId
import pl.przemyslawpitus.inventory.inventory.domain.category.CategoryNotFound
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItem
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class CreateParentItemEndpoint(
    private val createParentItemUseCase: CreateParentItemUseCase,
    private val errorHandler: ErrorHandler,
) {
    @PostMapping(
        "/parent-items",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createItem(
        @RequestBody request: CreateParentItemRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Create parent item | ${request.name}")

        try {
            val item = createParentItemUseCase.createParentItem(
                itemDraft = request.toParentItemDraft(),
                userId = userDetails.id,
            )

            return ResponseEntity.ok(
                item.toCreateParentItemResponse()
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

data class CreateParentItemRequest(
    val name: String,
    val categoryId: String,
) {
    fun toParentItemDraft() = ParentItemDraft(
        name = name,
        categoryId = CategoryId(categoryId),
    )
}

data class CreateParentItemResponse(
    val id: String,
    val name: String,
)

private fun ParentItem.toCreateParentItemResponse() =
    CreateParentItemResponse(
        id = this.id.value,
        name = this.name
    )