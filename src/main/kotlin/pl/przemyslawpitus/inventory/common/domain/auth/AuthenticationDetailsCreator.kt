package pl.przemyslawpitus.inventory.common.domain.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.common.domain.user.User
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import java.time.Instant
import java.util.*

class AuthenticationDetailsCreator(
    private val authenticationProperties: AuthenticationProperties,
) {
    fun createAuthenticationDetails(user: User): AuthenticationDetails {
        val accessToken = createAccessToken(
            id = user.id,
            username = user.username,
        )
        val refreshToken = createRefreshToken(
            id = user.id,
        )

        return AuthenticationDetails(
            username = user.username,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    private fun createAccessToken(id: UserId, username: String): String {
        return Jwts.builder()
            .setIssuer(authenticationProperties.issuer)
            .setSubject(id.value)
            .claim("username", username)
            .setExpiration(Date.from(Instant.now().plus(authenticationProperties.accessTokenExpirationTime)))
            .signWith(SignatureAlgorithm.HS256, authenticationProperties.secret)
            .compact()
    }

    private fun createRefreshToken(id: UserId): String {
        return Jwts.builder()
            .setIssuer(authenticationProperties.issuer)
            .setSubject(id.value)
            .setExpiration(Date.from(Instant.now().plus(authenticationProperties.refreshTokenExpirationTime)))
            .signWith(SignatureAlgorithm.HS256, authenticationProperties.secret)
            .compact()
    }
}