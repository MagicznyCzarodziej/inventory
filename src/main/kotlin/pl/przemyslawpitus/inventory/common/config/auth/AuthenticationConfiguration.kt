package pl.przemyslawpitus.inventory.common.config.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.przemyslawpitus.inventory.common.api.auth.JwtVerifierFilter
import pl.przemyslawpitus.inventory.common.domain.auth.AuthTokenVerifier
import java.time.Duration

@Configuration
@EnableConfigurationProperties(AuthenticationProperties::class)
class AuthenticationConfiguration {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}

@ConfigurationProperties(prefix = "authentication.jwt")
data class AuthenticationProperties(
    val issuer: String,
    val secret: String,
    val accessTokenCookieName: String,
    val refreshTokenCookieName: String,
    val accessTokenExpirationTime: Duration,
    val refreshTokenExpirationTime: Duration,
)

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    private val unprotectedPaths = "/auth/**"

    @Bean
    fun jwtVerifierFilter(
        authTokenVerifier: AuthTokenVerifier,
        authenticationProperties: AuthenticationProperties,
    ) = JwtVerifierFilter(
        authTokenVerifier = authTokenVerifier,
        authenticationProperties = authenticationProperties,
        ignoredPaths = unprotectedPaths
    )

    @Bean
    fun springWebFilterChain(
        http: HttpSecurity,
        jwtVerifierFilter: JwtVerifierFilter,
    ): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(unprotectedPaths, "/error").permitAll() //https://stackoverflow.com/a/75237203
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtVerifierFilter,
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .build()
    }
}

