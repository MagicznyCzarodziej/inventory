package pl.przemyslawpitus.inventory.domain.auth.registerUseCase

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.przemyslawpitus.inventory.domain.user.User
import pl.przemyslawpitus.inventory.domain.user.UserId
import pl.przemyslawpitus.inventory.domain.user.UserRepository
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.time.Instant

class RegisterUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    fun register(username: String, password: String) {
        logger.domain("Registering user | $username")

        val passwordHash = passwordEncoder.encode(password)
        val user = User(
            id = UserId(randomUuid()),
            username = username,
            passwordHash = passwordHash,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            version = 0,
        )

        userRepository.save(user)
    }

    private companion object : WithLogger()
}
