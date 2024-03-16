package pl.przemyslawpitus.inventory.common.domain.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.common.api.auth.InvalidTokenException

class AuthTokenVerifier(
    private val authenticationProperties: AuthenticationProperties,
) {
    fun verifyTokenAndGetClaims(jws: String): Claims {
        try {
            return Jwts.parser()
                .setSigningKey(authenticationProperties.secret)
                .parseClaimsJws(jws)
                .body
        } catch (exception: JwtException) {
            throw InvalidTokenException(exception)
        }
    }
}

