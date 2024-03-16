package pl.przemyslawpitus.inventory.sponges.domain.updateSpongeUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.sponges.domain.SpongeId
import pl.przemyslawpitus.inventory.sponges.domain.SpongeRepository
import pl.przemyslawpitus.inventory.sponges.domain.SpongeValidator
import java.time.Instant

class UpdateSpongeUseCase(
    private val spongeRepository: SpongeRepository,
) {
    fun updateSponge(spongeUpdate: SpongeUpdate, userId: UserId) {
        val sponge = spongeRepository.getById(spongeUpdate.spongeId)
            ?: throw SpongeNotFoundException(spongeUpdate.spongeId)

        if (sponge.userId != userId) {
            throw SpongeDoesNotBelongToUserException(
                spongeId = spongeUpdate.spongeId,
                userId = userId
            )
        }

        SpongeValidator.validate(color = spongeUpdate.color, purpose = spongeUpdate.purpose)

        val updatedSponge = sponge.copy(
            color = spongeUpdate.color,
            purpose = spongeUpdate.purpose,
            updatedAt = Instant.now(),
        )

        spongeRepository.save(updatedSponge)
    }
}

data class SpongeUpdate(
    val spongeId: SpongeId,
    val color: String,
    val purpose: String,
)

class SpongeNotFoundException(spongeId: SpongeId) :
    RuntimeException("Sponge ${spongeId.value} not found")

class SpongeDoesNotBelongToUserException(spongeId: SpongeId, userId: UserId) :
    RuntimeException("Sponge ${spongeId.value} doesn't belong to user ${userId.value}")
