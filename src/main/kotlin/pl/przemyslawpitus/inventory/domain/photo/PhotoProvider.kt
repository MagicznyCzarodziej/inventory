package pl.przemyslawpitus.inventory.domain.photo

import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.user.UserId

class PhotoProvider(
    private val photoRepository: PhotoRepository,
) {
    fun getPhotoByIdForUser(photoId: PhotoId, userId: UserId): Photo {
        val photo = photoRepository.getById(photoId) ?: throw PhotoNotFound(photoId)

        if (photo.userId != userId) throw PhotoDoesNotBelongToUser(photoId, userId)

        return photo
    }
}

class PhotoNotFound(photoId: PhotoId) : RuntimeException("Photo $photoId not found")

class PhotoDoesNotBelongToUser(photoId: PhotoId, userId: UserId) :
    RuntimeException("Photo $photoId doesn't belong to user $userId")