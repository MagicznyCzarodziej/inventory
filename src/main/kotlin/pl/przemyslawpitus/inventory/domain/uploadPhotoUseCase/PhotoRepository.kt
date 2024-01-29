package pl.przemyslawpitus.inventory.domain.uploadPhotoUseCase

import pl.przemyslawpitus.inventory.domain.item.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId

interface PhotoRepository {
    fun uploadPhoto(photo: Photo): Photo
    fun getById(photoId: PhotoId): Photo?
}