package pl.przemyslawpitus.inventory.api

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.przemyslawpitus.inventory.domain.getPhotoUseCase.GetPhotoUseCase
import pl.przemyslawpitus.inventory.logging.WithLogger
import java.io.File
import java.nio.charset.Charset

@RestController
class GetPhotoEndpoint(
    private val getPhotoUseCase: GetPhotoUseCase,
) {
    @GetMapping(
        "/photos/{photoId}",
        produces = [MediaType.IMAGE_JPEG_VALUE],
    )
    fun getItem(
        @PathVariable photoId: String,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        logger.api("Get photo | $photoId")

//        val photo = getPhotoUseCase.getPhoto(PhotoId(photoId))
        val path = request.servletContext.getRealPath("/inventory-images/")
        val photo = File(path + photoId)

        return ResponseEntity(
            photo.readBytes(),
            HttpHeaders().apply {
                contentDisposition = ContentDisposition.builder("inline")
                    .filename("plik", Charset.forName("UTF-8"))
                    .build()
            },
            HttpStatus.OK
        )


    }

    private companion object : WithLogger()
}