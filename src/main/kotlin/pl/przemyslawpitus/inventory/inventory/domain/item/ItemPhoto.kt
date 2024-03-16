package pl.przemyslawpitus.inventory.inventory.domain.item

data class ItemPhoto(
    val id: pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId,
    val url: String,
)

data class PhotoId(val value: String) {
    companion object {
        fun orNull(value: String?) = value?.let { pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId(value = it) }
    }
}

