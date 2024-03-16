package pl.przemyslawpitus.inventory.inventory.api.photo

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import pl.przemyslawpitus.inventory.common.domain.user.UserDetails
import pl.przemyslawpitus.inventory.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.inventory.domain.photo.uploadPhotoUseCase.UploadPhotoUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger

@RestController
class UploadPhotoEndpoint(
    private val uploadPhotoUseCase: UploadPhotoUseCase,
) {
    @PostMapping(
        "/photos",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun uploadPhoto(
        request: HttpServletRequest,
        @RequestParam file: MultipartFile,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<*> {
        logger.api("Upload file | ${file.name}")

        val photo = uploadPhotoUseCase.uploadPhoto(
            fileContent = file.bytes,
            userId = userDetails.id,
        )

        return ResponseEntity.ok(
            photo.toUploadPhotoResponse()
        )
    }

    private companion object : WithLogger()
}

data class UploadPhotoResponse(
    val photoId: String,
)

private fun Photo.toUploadPhotoResponse() = UploadPhotoResponse(
    photoId = this.id.value,
)