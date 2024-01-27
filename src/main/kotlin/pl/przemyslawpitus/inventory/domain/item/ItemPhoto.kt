package pl.przemyslawpitus.inventory.domain.item

data class ItemPhoto(
    val photoId: PhotoId,
    val photoUrl: String,
)

data class PhotoId(val value: String) {
    companion object {
        fun orNull(value: String?) = value?.let { PhotoId(value = it) }
    }
}

