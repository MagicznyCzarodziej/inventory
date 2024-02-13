package pl.przemyslawpitus.inventory.domain.user

import java.time.Instant

data class UserId(val value: String)

data class User(
    val id: UserId,
    val username: String,
    val passwordHash: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val version: Long,
)

data class UserDetails(
    val id: UserId,
    val username: String,
)
