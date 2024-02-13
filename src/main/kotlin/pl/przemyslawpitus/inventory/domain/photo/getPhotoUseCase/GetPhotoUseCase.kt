package pl.przemyslawpitus.inventory.domain.photo.getPhotoUseCase

import pl.przemyslawpitus.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.photo.uploadPhotoUseCase.PhotoRepository
import pl.przemyslawpitus.inventory.domain.user.UserId

class GetPhotoUseCase(
    private val photoRepository: PhotoRepository,
) {
    fun getPhoto(photoId: PhotoId, userId: UserId): Photo {
        val photo = photoRepository.getById(photoId) ?: throw PhotoNotFound(photoId)

        if (photo.userId != userId) throw PhotoDoesNotBelongToUser(photoId, userId)

        return photo
    }
}

class PhotoNotFound(photoId: PhotoId) : RuntimeException("Photo $photoId not found")

class PhotoDoesNotBelongToUser(photoId: PhotoId, userId: UserId) :
    RuntimeException("Photo $photoId doesn't belong to user $userId")