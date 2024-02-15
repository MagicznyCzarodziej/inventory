package pl.przemyslawpitus.inventory.domain.photo.getPhotoUseCase

import pl.przemyslawpitus.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.photo.PhotoProvider
import pl.przemyslawpitus.inventory.domain.user.UserId

class GetPhotoUseCase(
    private val photoProvider: PhotoProvider,
) {
    fun getPhoto(photoId: PhotoId, userId: UserId): Photo {
        return photoProvider.getPhotoByIdForUser(photoId, userId)
    }
}
