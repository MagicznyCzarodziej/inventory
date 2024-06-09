package pl.przemyslawpitus.inventory.common.api.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter
import pl.przemyslawpitus.inventory.common.api.ErrorHandler
import pl.przemyslawpitus.inventory.common.api.ErrorResponse
import pl.przemyslawpitus.inventory.common.config.auth.AuthenticationProperties
import pl.przemyslawpitus.inventory.common.domain.auth.AuthTokenVerifier
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.logging.WithLogger

class JwtVerifierFilter(
    private val authTokenVerifier: AuthTokenVerifier,
    private val authenticationProperties: AuthenticationProperties,
    private val ignoredPaths: String,
    private val objectMapper: ObjectMapper,
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
        } catch (exception: ExpiredTokenException) {
            JwtVerifierFilter.logger.apiError("Expired access token")

            val errorResponse = ErrorResponse(
                error = ErrorResponse.Error(
                    code = "EXPIRED_ACCESS_TOKEN",
                    message = "Expired access token",
                )
            )

            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write(objectMapper.writeValueAsString(errorResponse));
            return
        } catch (exception: MissingAccessTokenException) {
            JwtVerifierFilter.logger.apiError("Expired access token")

            val errorResponse = ErrorResponse(
                error = ErrorResponse.Error(
                    code = "MISSING_ACCESS_TOKEN",
                    message = "Missing access token",
                )
            )

            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write(objectMapper.writeValueAsString(errorResponse));
            return
        } catch (exception: InvalidTokenException) {
            JwtVerifierFilter.logger.apiError("Expired access token")

            val errorResponse = ErrorResponse(
                error = ErrorResponse.Error(
                    code = "INVALID_ACCESS_TOKEN",
                    message = "Invalid access token",
                )
            )

            response.status = HttpStatus.UNAUTHORIZED.value()
            response.writer.write(objectMapper.writeValueAsString(errorResponse));
            return
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

    private companion object : WithLogger()
}

class InvalidTokenException(exception: Exception) : RuntimeException("Invalid token", exception)

class ExpiredTokenException(exception: Exception) : RuntimeException("Expired token", exception)

class MissingAccessTokenException : RuntimeException("Missing access token")

class UsernameMissingFromTokenClaimsException : RuntimeException("Token claims are missing username field")