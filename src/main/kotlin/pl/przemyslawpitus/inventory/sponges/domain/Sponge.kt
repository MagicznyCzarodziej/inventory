package pl.przemyslawpitus.inventory.sponges.domain

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import java.time.Instant

data class Sponge (
    val id: SpongeId,
    val userId: UserId,
    val color: String,
    val purpose: String,
    val updatedAt: Instant,
    val version: Long,
)

data class SpongeId(val value: String)