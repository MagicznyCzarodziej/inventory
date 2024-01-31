package pl.przemyslawpitus.inventory.infrastructure.memory

import pl.przemyslawpitus.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase.PhotoRepository

class InMemoryPhotoRepository : PhotoRepository {
    private val photos = mutableMapOf<PhotoId, Photo>()

    override fun save(photo: Photo): Photo {
        if (photos.containsKey(photo.id)) throw RuntimeException("Photo ${photo.id} already exists")
        photos[photo.id] = photo;
        return photo
    }

    override fun getById(photoId: PhotoId): Photo? {
        return photos[photoId]
    }
}