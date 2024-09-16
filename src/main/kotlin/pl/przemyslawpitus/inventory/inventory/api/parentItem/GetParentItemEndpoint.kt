package pl.przemyslawpitus.inventory.inventory.api.parentItem

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemDoesNotBelongToUser
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemId
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.ParentItemNotFound
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemWithSubItemsCountUseCase.GetParentItemWithSubItemsCountUseCase
import pl.przemyslawpitus.inventory.inventory.domain.parentItem.getParentItemWithSubItemsCountUseCase.ParentItemWithSubItemsCount
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

@RestController
class GetParentItemEndpoint(
    private val getParentItemWithSubItemsCountUseCase: GetParentItemWithSubItemsCountUseCase,
    private val errorHandler: ErrorHandler,
) {
    @GetMapping(
        "/parent-items/{parentItemId}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getItem(
        @PathVariable parentItemId: String,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Get parent item | $parentItemId")

        try {
            val parentItem = getParentItemWithSubItemsCountUseCase.getParentItem(
                parentItemId = ParentItemId(parentItemId),
                userId = userDetails.id,
            )

            return ResponseEntity.ok(
                parentItem.toGetParentItemResponse()
            )
        } catch (exception: ParentItemNotFound) {
            return handleParentItemNotFound(exception)
        } catch (exception: ParentItemDoesNotBelongToUser) {
            return handleParentItemNotFound(exception)
        }
    }

    private fun handleParentItemNotFound(exception: Exception) =
        errorHandler.handleError(
            code = "PARENT_ITEM_NOT_FOUND",
            status = HttpStatus.NOT_FOUND,
            message = "Parent item not found",
            exception = exception,
        )

    private companion object : WithLogger()
}

data class GetParentItemResponse(
    val id: String,
    val name: String,
    val category: Category,
    val subItemsCount: Long,
) {
    data class Category(
        val id: String,
        val name: String,
    )
}

private fun ParentItemWithSubItemsCount.toGetParentItemResponse() = GetParentItemResponse(
    id = id.value,
    name = name,
    category = GetParentItemResponse.Category(
        id = category.id.value,
        name = category.name,
    ),
    subItemsCount = subItemsCount,
)
