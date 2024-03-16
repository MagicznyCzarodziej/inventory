package pl.przemyslawpitus.inventory.inventory.domain.photo

import pl.przemyslawpitus.inventory.common.domain.user.UserId

class PhotoProvider(
    private val photoRepository: PhotoRepository,
) {
    fun getPhotoByIdForUser(photoId: pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId, userId: UserId): Photo {
        val photo = photoRepository.getById(photoId) ?: throw PhotoNotFound(photoId)

        if (photo.userId != userId) throw PhotoDoesNotBelongToUser(photoId, userId)

        return photo
    }
}

class PhotoNotFound(photoId: pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId) : RuntimeException("Photo $photoId not found")

class PhotoDoesNotBelongToUser(photoId: pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId, userId: UserId) :
    RuntimeException("Photo $photoId doesn't belong to user $userId")