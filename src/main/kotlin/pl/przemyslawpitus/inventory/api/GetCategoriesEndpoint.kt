package pl.przemyslawpitus.inventory.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.getCategoriesUseCase.GetCategoriesUseCase
import pl.przemyslawpitus.inventory.domain.item.Category
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class GetCategoriesEndpoint(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) {
    @GetMapping(
        "/categories",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getCategoriesItems(): ResponseEntity<*> {
        logger.api("Get categories items")

        val categories = getCategoriesUseCase.getCategories()

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