package pl.przemyslawpitus.inventory.sponges.infrastructure.mongodb

import org.springframework.data.mongodb.core.MongoTemplate
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.common.infrastructure.mongodb.queryByUserId
import pl.przemyslawpitus.inventory.sponges.domain.Sponge
import pl.przemyslawpitus.inventory.sponges.domain.SpongeId
import pl.przemyslawpitus.inventory.sponges.domain.SpongeRepository
import java.time.Instant

class MongoSpongeRepository(
    private val mongoTemplate: MongoTemplate,
) : SpongeRepository {
    override fun getById(spongeId: SpongeId): Sponge? {
        return mongoTemplate.findById(spongeId.value, SpongeEntity::class.java)?.toDomain()
    }

    override fun getAllByUserId(userId: UserId): List<Sponge> {
        return mongoTemplate.find(queryByUserId(userId), SpongeEntity::class.java).toDomain()
    }

    override fun save(sponge: Sponge): Sponge {
        return mongoTemplate.save(sponge.toEntity()).toDomain()
    }
}

data class SpongeEntity(
    val id: String,
    val userId: String,
    val color: String,
    val purpose: String,
    val updatedAt: Instant,
    val version: Long,
) {
    fun toDomain() = Sponge(
        id = SpongeId(this.id),
        userId = UserId(this.userId),
        color = this.color,
        purpose = this.purpose,
        updatedAt = this.updatedAt,
        version = this.version,
    )
}

private fun List<SpongeEntity>.toDomain() = this.map { it.toDomain() }

private fun Sponge.toEntity() = SpongeEntity(
    id = this.id.value,
    userId = this.userId.value,
    color = this.color,
    purpose = this.purpose,
    updatedAt = this.updatedAt,
    version = this.version,
)
