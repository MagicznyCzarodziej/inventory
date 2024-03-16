package pl.przemyslawpitus.inventory.inventory.domain.photo.getPhotoUseCase

import pl.przemyslawpitus.inventory.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.inventory.domain.photo.PhotoProvider
import pl.przemyslawpitus.inventory.common.domain.user.UserId

class GetPhotoUseCase(
    private val photoProvider: PhotoProvider,
) {
    fun getPhoto(photoId: pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId, userId: UserId): Photo {
        return photoProvider.getPhotoByIdForUser(photoId, userId)
    }
}
