package pl.przemyslawpitus.inventory.domain.auth.refreshTokenUseCase

import pl.przemyslawpitus.inventory.domain.auth.AuthTokenVerifier
import pl.przemyslawpitus.inventory.domain.auth.AuthenticationDetails
import pl.przemyslawpitus.inventory.domain.auth.AuthenticationDetailsCreator
import pl.przemyslawpitus.inventory.domain.user.UserId
import pl.przemyslawpitus.inventory.domain.user.UserNotFoundException
import pl.przemyslawpitus.inventory.domain.user.UserRepository

class RefreshTokenUseCase(
    private val authTokenVerifier: AuthTokenVerifier,
    private val userRepository: UserRepository,
    private val authenticationDetailsCreator: AuthenticationDetailsCreator,
) {
    fun refreshToken(refreshToken: String): AuthenticationDetails {
        val claims = authTokenVerifier.verifyTokenAndGetClaims(refreshToken)
        val userId = UserId(claims.subject)
        val user = userRepository.getById(userId) ?: throw UserNotFoundException(userId = userId)

        return authenticationDetailsCreator.createAuthenticationDetails(user = user)
    }
}