package pl.przemyslawpitus.inventory.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.common.domain.auth.AuthTokenVerifier
import pl.przemyslawpitus.inventory.common.domain.auth.AuthenticationDetailsCreator
import pl.przemyslawpitus.inventory.common.domain.auth.loginUseCase.LoginUseCase
import pl.przemyslawpitus.inventory.common.domain.auth.refreshTokenUseCase.RefreshTokenUseCase
import pl.przemyslawpitus.inventory.common.domain.auth.registerUseCase.RegisterUseCase
import pl.przemyslawpitus.inventory.common.domain.user.UserRepository

@Configuration
class CommonDomainConfig {
    @Bean
    fun loginUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
        authenticationDetailsCreator: AuthenticationDetailsCreator,
    ) = LoginUseCase(
        userRepository = userRepository,
        passwordEncoder = passwordEncoder,
        authenticationDetailsCreator = authenticationDetailsCreator,
    )

    @Bean
    fun refreshTokenUseCase(
        authTokenVerifier: AuthTokenVerifier,
        userRepository: UserRepository,
        authenticationDetailsCreator: AuthenticationDetailsCreator,
    ) = RefreshTokenUseCase(
        authTokenVerifier = authTokenVerifier,
        userRepository = userRepository,
        authenticationDetailsCreator = authenticationDetailsCreator,
    )

    @Bean
    fun authTokenVerifier(
        authenticationProperties: AuthenticationProperties,
    ) = AuthTokenVerifier(
        authenticationProperties = authenticationProperties,
    )

    @Bean
    fun registerUseCase(
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
    ) = RegisterUseCase(
        userRepository = userRepository,
        passwordEncoder = passwordEncoder,
    )

    @Bean
    fun authenticationDetailsCreator(
        authenticationProperties: AuthenticationProperties,
    ) = AuthenticationDetailsCreator(
        authenticationProperties = authenticationProperties,
    )

}