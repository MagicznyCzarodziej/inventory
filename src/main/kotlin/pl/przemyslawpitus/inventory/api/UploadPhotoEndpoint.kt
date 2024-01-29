package pl.przemyslawpitus.inventory.api

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import pl.przemyslawpitus.inventory.domain.item.Photo
import pl.przemyslawpitus.inventory.domain.uploadPhotoUseCase.UploadPhotoUseCase
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.io.File

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
        @RequestParam("file") file: MultipartFile,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        logger.api("Upload file | ${file.name}")
        val path = request.servletContext.getRealPath("/inventory-images/")
        logger.api("path: $path")
        if(!File(path).exists()) {
            File(path).mkdir();
        }

        val id = randomUuid()
        val filePath: String = path + id
        val dest = File(filePath)
        file.transferTo(dest)
//        val photo = uploadPhotoUseCase.uploadPhoto(file)

        return ResponseEntity.ok(
            UploadPhotoResponse(
                photoId = id
            )
//            photo.toUploadPhotoResponse()
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