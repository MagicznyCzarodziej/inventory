package pl.przemyslawpitus.inventory.common.api.auth

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
@RequestMapping("/auth")
class LogoutEndpoint(
    private val authenticationProperties: AuthenticationProperties,
) {
    @PostMapping("/logout")
    fun logout(): ResponseEntity<Void> {
        logger.api("Logout")

        val expiredAccessToken = createExpiredCookie(authenticationProperties.accessTokenCookieName)
        val expiredRefreshToken = createExpiredCookie(authenticationProperties.refreshTokenCookieName)

        return ResponseEntity
            .noContent()
            .header(HttpHeaders.SET_COOKIE, expiredAccessToken)
            .header(HttpHeaders.SET_COOKIE, expiredRefreshToken)
            .build()
    }

    private companion object : WithLogger()
}