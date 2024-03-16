package pl.przemyslawpitus.inventory.common.domain.auth.loginUseCase

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.przemyslawpitus.inventory.common.domain.auth.AuthenticationDetails
import pl.przemyslawpitus.inventory.common.domain.auth.AuthenticationDetailsCreator
import pl.przemyslawpitus.inventory.common.domain.user.UserNotFoundException
import pl.przemyslawpitus.inventory.common.domain.user.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationDetailsCreator: AuthenticationDetailsCreator,
) {
    fun login(credentials: Credentials): AuthenticationDetails {
        val user = userRepository.getByUsername(credentials.username)
            ?: throw UserNotFoundException(username = credentials.username)

        val passwordsMatch = passwordEncoder.matches(
            credentials.password,
            user.passwordHash,
        )
        if (!passwordsMatch) throw InvalidPasswordException(credentials.username)

        return authenticationDetailsCreator.createAuthenticationDetails(user = user)
    }
}

data class Credentials(
    val username: String,
    val password: String,
)

class InvalidPasswordException(val username: String) : RuntimeException("Invalid password for user $username")
