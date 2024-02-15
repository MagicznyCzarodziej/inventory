package pl.przemyslawpitus.inventory.domain.photo

import pl.przemyslawpitus.inventory.domain.item.PhotoId

interface PhotoRepository {
    fun save(photo: Photo): Photo
    fun getById(photoId: PhotoId): Photo?
}