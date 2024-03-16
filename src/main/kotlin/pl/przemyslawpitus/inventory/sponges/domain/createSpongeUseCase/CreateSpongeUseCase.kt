package pl.przemyslawpitus.inventory.sponges.domain.createSpongeUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.common.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.sponges.domain.Sponge
import pl.przemyslawpitus.inventory.sponges.domain.SpongeId
import pl.przemyslawpitus.inventory.sponges.domain.SpongeRepository
import pl.przemyslawpitus.inventory.sponges.domain.SpongeValidator
import java.time.Instant

class CreateSpongeUseCase(
    private val spongeRepository: SpongeRepository,
) {
    fun createSponge(spongeDraft: SpongeDraft, userId: UserId): Sponge {
        SpongeValidator.validate(color = spongeDraft.color, purpose = spongeDraft.purpose)

        val sponge = Sponge(
            id = SpongeId(randomUuid()),
            userId = userId,
            color = spongeDraft.color,
            purpose = spongeDraft.purpose,
            updatedAt = Instant.now(),
            version = 0,
        )

        return spongeRepository.save(sponge)
    }
}

data class SpongeDraft(
    val color: String,
    val purpose: String,
)