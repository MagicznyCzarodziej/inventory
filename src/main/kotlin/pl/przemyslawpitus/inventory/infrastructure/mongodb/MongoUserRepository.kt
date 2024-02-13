package pl.przemyslawpitus.inventory.infrastructure.mongodb

import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import pl.przemyslawpitus.inventory.domain.user.User
import pl.przemyslawpitus.inventory.domain.user.UserId
import pl.przemyslawpitus.inventory.domain.user.UserRepository
import java.time.Instant

class MongoUserRepository(
    private val mongoTemplate: MongoTemplate,
) : UserRepository {
    override fun save(user: User) {
        mongoTemplate.save(user.toEntity())
    }

    override fun getById(userId: UserId): User? {
        return mongoTemplate.findById(userId.value, UserEntity::class.java)?.toDomain()
    }

    override fun getByUsername(username: String): User? {
        return mongoTemplate.findOne(
            queryByUsername(username),
            UserEntity::class.java
        )?.toDomain()
    }

    private fun queryByUsername(username: String) = Query()
        .addCriteria(
            Criteria.where("username").isEqualTo(username)
        )
}

@Document("users")
data class UserEntity(
    val id: String,
    val username: String,
    val passwordHash: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    @Version val version: Long,
) {
    fun toDomain() = User(
        id = UserId(this.id),
        username = this.username,
        passwordHash = this.passwordHash,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        version = this.version
    )
}

private fun User.toEntity() = UserEntity(
    id = this.id.value,
    username = this.username,
    passwordHash = this.passwordHash,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    version = this.version,
)