package pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase

import pl.przemyslawpitus.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger

class UploadPhotoUseCase(
    private val photoRepository: PhotoRepository,
) {
    fun uploadPhoto(fileContent: ByteArray): Photo {
        val photo = Photo(
            id = PhotoId(randomUuid()),
            file = fileContent,
        )

        val savedPhoto = photoRepository.save(photo)
        logger.domain("Saved photo | ${photo.id}")

        return savedPhoto
    }

    private companion object : WithLogger()
}