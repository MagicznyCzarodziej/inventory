package pl.przemyslawpitus.inventory.sponges.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.logging.WithLogger
import pl.przemyslawpitus.inventory.sponges.domain.Sponge
import pl.przemyslawpitus.inventory.sponges.domain.SpongeId
import pl.przemyslawpitus.inventory.sponges.domain.createSpongeUseCase.CreateSpongeUseCase
import pl.przemyslawpitus.inventory.sponges.domain.createSpongeUseCase.SpongeDraft
import pl.przemyslawpitus.inventory.sponges.domain.getSpongesUseCase.GetSpongesUseCase
import pl.przemyslawpitus.inventory.sponges.domain.updateSpongeUseCase.SpongeUpdate
import pl.przemyslawpitus.inventory.sponges.domain.updateSpongeUseCase.UpdateSpongeUseCase

@RestController
class SpongesEndpoint(
    private val getSpongesUseCase: GetSpongesUseCase,
    private val createSpongeUseCase: CreateSpongeUseCase,
    private val updateSpongeUseCase: UpdateSpongeUseCase,
) {
    @GetMapping(
        "/sponges",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getSponges(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Get sponges")

        val sponges = getSpongesUseCase.getSponges(userId = userDetails.id)

        return ResponseEntity.ok(
            sponges.toGetSpongesResponse()
        )

        // TODO Add errors handling
    }

    @PostMapping(
        "/sponges",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun createSponge(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: CreateSpongeRequest,
    ): ResponseEntity<*> {
        logger.api("Create sponge")

        createSpongeUseCase.createSponge(
            spongeDraft = request.toSpongeDraft(),
            userId = userDetails.id,
        )

        return ResponseEntity.noContent().build<Unit>()

        // TODO Add errors handling
    }

    @PutMapping(
        "/sponges/{spongeId}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun updateSponge(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable spongeId: String,
        @RequestBody request: SpongeUpdateRequest,
    ): ResponseEntity<*> {
        logger.api("Update sponge $spongeId")

        updateSpongeUseCase.updateSponge(
            spongeUpdate = request.toSpongeUpdate(SpongeId(spongeId)),
            userId = userDetails.id,
        )

        return ResponseEntity.noContent().build<Unit>()

        // TODO Add errors handling
    }

    private companion object : WithLogger()
}

data class GetSpongesResponse(
    val sponges: List<SpongeDto>,
) {
    data class SpongeDto(
        val id: String,
        val color: String,
        val purpose: String,
    )
}

private fun List<Sponge>.toGetSpongesResponse() = GetSpongesResponse(
    sponges = this.map { it.toSpongeDto() }
)

private fun Sponge.toSpongeDto() = GetSpongesResponse.SpongeDto(
    id = this.id.value,
    color = this.color,
    purpose = this.purpose,
)

data class CreateSpongeRequest(
    val color: String,
    val purpose: String,
) {
    fun toSpongeDraft() = SpongeDraft(
        color = this.color,
        purpose = this.purpose,
    )
}

data class SpongeUpdateRequest(
    val color: String,
    val purpose: String,
) {
    fun toSpongeUpdate(spongeId: SpongeId) = SpongeUpdate(
        spongeId = spongeId,
        color = this.color,
        purpose = this.purpose,
    )
}
