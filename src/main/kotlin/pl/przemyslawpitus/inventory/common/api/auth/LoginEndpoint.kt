package pl.przemyslawpitus.inventory.common.api.auth

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.common.domain.auth.AuthenticationDetails
import pl.przemyslawpitus.inventory.common.domain.auth.loginUseCase.Credentials
import pl.przemyslawpitus.inventory.common.domain.user.UserNotFoundException
import pl.przemyslawpitus.inventory.common.domain.auth.loginUseCase.InvalidPasswordException
import pl.przemyslawpitus.inventory.common.domain.auth.loginUseCase.LoginUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
@RequestMapping("/auth")
class LoginEndpoint(
    private val loginUseCase: LoginUseCase,
    private val authenticationProperties: AuthenticationProperties,
) {
    @PostMapping(
        "/login",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun login(@RequestBody credentials: LoginRequest): ResponseEntity<*> {
        logger.api("Login | ${credentials.username}")
        try {
            val authentication = loginUseCase.login(credentials.toDomain())

            val accessTokenCookie =
                createSecureCookie(authenticationProperties.accessTokenCookieName, authentication.accessToken)
            val refreshTokenCookie =
                createSecureCookie(authenticationProperties.refreshTokenCookieName, authentication.refreshToken)

            return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .body(authentication.toResponse())
        } catch (exception: InvalidPasswordException) {
            logger.api("Invalid password for username ${exception.username}")
            return invalidCredentialsResponse()
        } catch (exception: UserNotFoundException) {
            logger.api("User not found for username ${exception.username}")
            return invalidCredentialsResponse()
        }
    }

    private fun invalidCredentialsResponse() = ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(
            InvalidLoginCredentialsResponse(error = "Invalid username or password")
        )

    private companion object : WithLogger()
}

data class LoginRequest(
    val username: String,
    val password: String,
) {
    fun toDomain() = Credentials(
        username = username,
        password = password,
    )
}

private data class LoginResponse(
    val username: String,
)

private fun AuthenticationDetails.toResponse() = LoginResponse(
    username = this.username
)

private data class InvalidLoginCredentialsResponse(
    val error: String,
)
