package pl.przemyslawpitus.inventory.common.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import pl.przemyslawpitus.inventory.common.domain.auth.registerUseCase.RegisterUseCase
import pl.przemyslawpitus.inventory.common.domain.user.UserRepository
import pl.przemyslawpitus.inventory.logging.WithLogger

@Configuration
@Profile("local")
class TestDataSeeder {
    @Bean
    fun seedTestUser(
        registerUseCase: RegisterUseCase,
        userRepository: UserRepository,
    ) = CommandLineRunner {
        val testUsername = "testuser"
        val testPassword = "testpassword"

        val existingUser = userRepository.getByUsername(testUsername)
        if (existingUser != null) {
            logger.info("Test user '$testUsername' already exists, skipping seed test")
            return@CommandLineRunner
        }

        registerUseCase.register(testUsername, testPassword)
        logger.info("Test user '$testUsername' was created successfully")
    }

    private companion object : WithLogger()
}