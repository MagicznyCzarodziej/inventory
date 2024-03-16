package pl.przemyslawpitus.inventory.inventory.api.category

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.category.createCategoryUseCase.CreateCategoryUseCase
import pl.przemyslawpitus.inventory.inventory.domain.category.Category
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class CreateCategoryEndpoint(
    private val createCategoryUseCase: CreateCategoryUseCase,
) {
    @PostMapping(
        "/category",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun createCategory(
        @RequestBody request: CreateCategoryRequest,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Create category | ${request.name}")

        val category = createCategoryUseCase.createCategory(
            categoryName = request.name,
            userId = userDetails.id,
        )

        return ResponseEntity.ok().body(category.toResponse())
    }

    private companion object : WithLogger()
}

data class CreateCategoryRequest(
    val name: String,
)

data class CreateCategoryResponse(
    val id: String,
    val name: String,
)

private fun Category.toResponse() =
    CreateCategoryResponse(
        id = this.id.value,
        name = this.name,
    )