package jp.ac.titech.itsp.libermo.services

import jp.ac.titech.itsp.libermo.models.Image
import jp.ac.titech.itsp.libermo.repositories.ImageRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {

    fun create(image: MultipartFile): Image {
        saveFile(image)
        return imageRepository.save(Image(image.originalFilename ?: "file"))
    }

    fun saveFile(image: MultipartFile) {
        // TODO
    }

}