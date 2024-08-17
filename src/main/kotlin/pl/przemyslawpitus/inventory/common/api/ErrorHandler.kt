package pl.przemyslawpitus.inventory.common.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.lang.Exception

class ErrorHandler {
    fun handleError(
        code: String,
        status: HttpStatus,
        message: String,
        exception: Exception,
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = ErrorResponse.Error(
                code = code,
                message = message,
            )
        )

        logger.apiError("Code: $code, Message: ${errorResponse.error.message}, Exception message: ${exception.message}")

        return ResponseEntity
            .status(status)
            .body(
                errorResponse
            )
    }

    private companion object : WithLogger()
}

data class ErrorResponse(
    val error: Error,
) {
    data class Error(
        val code: String,
        val message: String,
    )
}