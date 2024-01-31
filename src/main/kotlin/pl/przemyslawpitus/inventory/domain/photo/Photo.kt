package pl.przemyslawpitus.inventory.domain.photo

import pl.przemyslawpitus.inventory.domain.item.PhotoId

data class Photo(
    val id: PhotoId,
    val file: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (id != other.id) return false
        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + file.contentHashCode()
        return result
    }
}