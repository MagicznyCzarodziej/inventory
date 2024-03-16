package pl.przemyslawpitus.inventory.common.domain.user

interface UserRepository {
    fun save(user: User)
    fun getById(userId: UserId): User?
    fun getByUsername(username: String): User?
}

class UserNotFoundException(
    val userId: UserId? = null,
    val username: String? = null,
) : RuntimeException("User not found | UserId: ${userId?.value}, Username: $username")