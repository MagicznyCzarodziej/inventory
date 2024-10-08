package pl.przemyslawpitus.inventory.common.api.auth

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseCookie

fun createSecureCookie(name: String, value: String) =
    ResponseCookie
        .from(
            name,
            value,
        )
        .secure(false)
//        .secure(true)
        .httpOnly(true)
        .path("/")
        .build()
        .toString()

fun extractCookieFromRequest(request: HttpServletRequest, cookieName: String): String? {
    return request.cookies
        ?.find { it.name == cookieName }
        ?.value
}