package pl.przemyslawpitus.inventory.common.api.auth

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.common.domain.auth.refreshTokenUseCase.RefreshTokenUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
@RequestMapping("/auth")
class RefreshTokenEndpoint(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val authenticationProperties: AuthenticationProperties,
    private val errorHandler: ErrorHandler,
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
            return errorHandler.handleError(
                code = "MISSING_REFRESH_TOKEN",
                status = HttpStatus.BAD_REQUEST,
                message = "Missing refresh token",
                exception = exception,
            )
        } catch (exception: ExpiredTokenException) {
            return errorHandler.handleError(
                code = "EXPIRED_REFRESH_TOKEN",
                status = HttpStatus.BAD_REQUEST,
                message = "Expired refresh token",
                exception = exception,
            )
        } catch (exception: InvalidTokenException) {
            return errorHandler.handleError(
                code = "INVALID_REFRESH_TOKEN",
                status = HttpStatus.BAD_REQUEST,
                message = "Invalid refresh token",
                exception = exception,
            )
        }
    }

    private companion object : WithLogger()
}

private class MissingRefreshTokenException : RuntimeException("Missing refresh token")
