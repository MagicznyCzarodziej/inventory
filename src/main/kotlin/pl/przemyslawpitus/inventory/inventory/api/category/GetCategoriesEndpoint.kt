package pl.przemyslawpitus.inventory.inventory.api.category

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.inventory.domain.category.getCategoriesUseCase.GetCategoriesUseCase
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class GetCategoriesEndpoint(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) {
    @GetMapping(
        "/categories",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getCategoriesItems(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Get categories items")

        val categories = getCategoriesUseCase.getCategories(userId = userDetails.id)

        return ResponseEntity.ok(
            categories.toGetCategoriesResponse()
        )
    }

    private companion object : WithLogger()
}

data class GetCategoriesResponse(
    val categories: List<CategoryDto>,
) {
    data class CategoryDto(
        val id: String,
        val name: String,
    )
}

fun List<Category>.toGetCategoriesResponse() = GetCategoriesResponse(
    categories = this.map {
        GetCategoriesResponse.CategoryDto(
            id = it.id.value,
            name = it.name,
        )
    }
)