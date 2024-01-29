package pl.przemyslawpitus.inventory.domain.uploadPhotoUseCase

import org.springframework.web.multipart.MultipartFile
import pl.przemyslawpitus.inventory.domain.item.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger

class UploadPhotoUseCase(
    private val photoRepository: PhotoRepository,
) {
    fun uploadPhoto(file: MultipartFile): Photo {
        val photo = Photo(
            id = PhotoId(randomUuid()),
            file = file,
        )

        val savedPhoto = photoRepository.uploadPhoto(photo)
        logger.domain("Saved photo | ${photo.id}")

        return savedPhoto
    }

    private companion object : WithLogger()
}