package pl.przemyslawpitus.inventory.common.api.auth

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class MeEndpoint {
    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<MeResponse> {
        logger.api("Me | ${userDetails.username}")

        return ResponseEntity.ok(
            MeResponse(username = userDetails.username)
        )
    }

    private companion object : WithLogger()
}

data class MeResponse(
    val username: String,
)