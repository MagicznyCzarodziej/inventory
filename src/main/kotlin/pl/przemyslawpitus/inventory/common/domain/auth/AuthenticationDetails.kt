package pl.przemyslawpitus.inventory.common.domain.auth

data class AuthenticationDetails(
    val username: String,
    val accessToken: String,
    val refreshToken: String,
)