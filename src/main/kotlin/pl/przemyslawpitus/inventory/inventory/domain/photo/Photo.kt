package pl.przemyslawpitus.inventory.inventory.domain.photo

import pl.przemyslawpitus.inventory.common.domain.user.UserId

data class Photo(
    val id: pl.przemyslawpitus.inventory.inventory.domain.item.PhotoId,
    val userId: UserId,
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