package pl.przemyslawpitus.inventory.infrastructure.mongodb

import org.bson.BsonBinarySubType
import org.bson.types.Binary
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import pl.przemyslawpitus.inventory.domain.user.UserId
import pl.przemyslawpitus.inventory.domain.photo.Photo
import pl.przemyslawpitus.inventory.domain.item.PhotoId
import pl.przemyslawpitus.inventory.domain.photo.PhotoRepository

class MongoPhotoRepository(
    private val mongoTemplate: MongoTemplate,
) : PhotoRepository {
    override fun save(photo: Photo): Photo {
        return mongoTemplate.save(photo.toEntity()).toDomain()
    }

    override fun getById(photoId: PhotoId): Photo? {
        return mongoTemplate.findById(photoId.value, PhotoEntity::class.java)?.toDomain()
    }
}

@Document("photos")
data class PhotoEntity(
    val id: String,
    val userId: String,
    val file: Binary,
) {
    fun toDomain() = Photo(
        id = PhotoId(this.id),
        userId = UserId(this.userId),
        file = this.file.data
    )
}

private fun Photo.toEntity() = PhotoEntity(
    id = this.id.value,
    userId = this.userId.value,
    file = Binary(BsonBinarySubType.BINARY, this.file),
)