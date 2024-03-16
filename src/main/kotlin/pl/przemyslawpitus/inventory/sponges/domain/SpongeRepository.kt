package pl.przemyslawpitus.inventory.sponges.domain

import pl.przemyslawpitus.inventory.common.domain.user.UserId

interface SpongeRepository {
    fun getById(spongeId: SpongeId): Sponge?
    fun getAllByUserId(userId: UserId): List<Sponge>
    fun save(sponge: Sponge): Sponge
}