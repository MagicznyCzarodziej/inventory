package pl.przemyslawpitus.inventory.common.domain.auth.refreshTokenUseCase

import pl.przemyslawpitus.inventory.common.domain.auth.AuthTokenVerifier
import pl.przemyslawpitus.inventory.common.domain.auth.AuthenticationDetails
import pl.przemyslawpitus.inventory.common.domain.auth.AuthenticationDetailsCreator
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.common.domain.user.UserNotFoundException
import pl.przemyslawpitus.inventory.common.domain.user.UserRepository

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