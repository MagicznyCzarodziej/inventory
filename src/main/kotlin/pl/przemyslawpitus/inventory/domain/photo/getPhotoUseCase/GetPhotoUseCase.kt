package pl.przemyslawpitus.inventory.domain.photo.getPhotoUseCase

import pl.przemyslawpitus.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase.PhotoRepository

class GetPhotoUseCase(
    private val photoRepository: PhotoRepository,
) {
    fun getPhoto(photoId: PhotoId): Photo {
        return photoRepository.getById(photoId) ?: throw RuntimeException("Photo $photoId not found")
    }
}