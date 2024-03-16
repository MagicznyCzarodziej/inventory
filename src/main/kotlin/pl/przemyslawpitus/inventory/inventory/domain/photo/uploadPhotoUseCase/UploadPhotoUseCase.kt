package pl.przemyslawpitus.inventory.inventory.domain.photo.uploadPhotoUseCase

import pl.przemyslawpitus.inventory.common.domain.user.UserId
import pl.przemyslawpitus.inventory.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.inventory.domain.photo.PhotoRepository
import pl.przemyslawpitus.inventory.common.domain.utils.randomUuid
import pl.przemyslawpitus.inventory.logging.WithLogger

class UploadPhotoUseCase(
    private val photoRepository: PhotoRepository,
) {
    fun uploadPhoto(fileContent: ByteArray, userId: UserId): Photo {
        val photo = Photo(
            id = pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId(randomUuid()),
            userId = userId,
            file = fileContent,
        )

        val savedPhoto = photoRepository.save(photo)
        logger.domain("Saved photo | ${photo.id}")

        return savedPhoto
    }

    private companion object : WithLogger()
}