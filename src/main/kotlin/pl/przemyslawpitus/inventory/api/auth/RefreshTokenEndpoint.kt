package pl.przemyslawpitus.inventory.api.auth

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.domain.auth.refreshTokenUseCase.RefreshTokenUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
@RequestMapping("/auth")
class RefreshTokenEndpoint(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val authenticationProperties: AuthenticationProperties
) {
    @PostMapping("/refresh-token")
    fun refreshToken(request: HttpServletRequest): ResponseEntity<*> {
        logger.api("Refresh token")
        try {
            val refreshToken = extractCookieFromRequest(
                request = request,
                cookieName = authenticationProperties.refreshTokenCookieName,
            ) ?: throw MissingRefreshTokenException()

            val authentication = refreshTokenUseCase.refreshToken(refreshToken = refreshToken)

            val accessTokenCookie =
                createSecureCookie(authenticationProperties.accessTokenCookieName, authentication.accessToken)
            val refreshTokenCookie =
                createSecureCookie(authenticationProperties.refreshTokenCookieName, authentication.refreshToken)

            return ResponseEntity
                .noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .build<Void>()
        } catch (exception: MissingRefreshTokenException) {
            logger.apiError(exception.message!!)
            return ResponseEntity
                .badRequest()
                .body(
                    MissingRefreshTokenResponse(
                        error = "Missing refresh token cookie (${authenticationProperties.refreshTokenCookieName})"
                    )
                )
        }
    }

    private companion object : WithLogger()
}

private class MissingRefreshTokenException : RuntimeException("Missing refresh token")

private data class MissingRefreshTokenResponse(
    val error: String,
)