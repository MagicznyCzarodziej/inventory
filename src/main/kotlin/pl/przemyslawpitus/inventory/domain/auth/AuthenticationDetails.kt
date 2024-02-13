package pl.przemyslawpitus.inventory.domain.auth

data class AuthenticationDetails(
    val username: String,
    val accessToken: String,
    val refreshToken: String,
)