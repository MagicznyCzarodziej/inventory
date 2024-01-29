package pl.przemyslawpitus.inventory.domain.item

import org.springframework.web.multipart.MultipartFile

data class Photo(
    val id: PhotoId,
    val file: MultipartFile,
)