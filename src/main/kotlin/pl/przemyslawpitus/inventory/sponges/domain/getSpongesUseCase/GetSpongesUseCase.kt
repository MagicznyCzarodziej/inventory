package pl.przemyslawpitus.inventory.sponges.domain.getSpongesUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.sponges.domain.Sponge
import pl.przemyslawpitus.inventory.sponges.domain.SpongeRepository

class GetSpongesUseCase(
    private val spongeRepository: SpongeRepository,
) {
    fun getSponges(userId: UserId): List<Sponge> {
        return spongeRepository.getAllByUserId(userId)
    }
}