package pl.przemyslawpitus.inventory.common.api.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.common.domain.auth.AuthTokenVerifier
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.common.domain.user.UserId

class JwtVerifierFilter(
    private val authTokenVerifier: AuthTokenVerifier,
    private val authenticationProperties: AuthenticationProperties,
    private val ignoredPaths: String,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (AntPathRequestMatcher(ignoredPaths).matches(request)) {
            return filterChain.doFilter(request, response)
        }

        try {
            authenticate(request)
        } catch (exception: InvalidTokenException) {
            logger.error(exception)
        } catch (exception: MissingAccessTokenException) {
            logger.error(exception)
        }

        filterChain.doFilter(request, response)
    }

    private fun authenticate(request: HttpServletRequest) {
        val accessToken = extractCookieFromRequest(
            request = request,
            cookieName = authenticationProperties.accessTokenCookieName,
        ) ?: throw MissingAccessTokenException()

        val claims = authTokenVerifier.verifyTokenAndGetClaims(accessToken)
        val username = claims.get("username", String::class.java) ?: throw UsernameMissingFromTokenClaimsException()

        val authentication = UsernamePasswordAuthenticationToken(
            UserDetails(
                id = UserId(claims.subject),
                username = username
            ),
            null,
            emptyList()
        )

        SecurityContextHolder.getContext().authentication = authentication
    }
}

class InvalidTokenException(exception: Exception) : RuntimeException("Invalid token", exception)

class MissingAccessTokenException : RuntimeException("Missing access token")

class UsernameMissingFromTokenClaimsException : RuntimeException("Token claims are missing username field")