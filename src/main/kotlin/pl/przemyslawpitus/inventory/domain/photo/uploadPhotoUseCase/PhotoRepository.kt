package pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase

import pl.przemyslawpitus.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId

interface PhotoRepository {
    fun save(photo: Photo): Photo
    fun getById(photoId: PhotoId): Photo?
}