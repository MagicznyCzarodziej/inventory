package pl.przemyslawpitus.inventory.domain.item

data class ItemPhoto(
    val id: PhotoId,
    val url: String,
)

data class PhotoId(val value: String) {
    companion object {
        fun orNull(value: String?) = value?.let { PhotoId(value = it) }
    }
}

