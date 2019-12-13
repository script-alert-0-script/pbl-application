package jp.ac.titech.itsp.libermo.services

import jp.ac.titech.itsp.libermo.models.Image
import jp.ac.titech.itsp.libermo.repositories.ImageRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {

    val imageFolder get() = "./images"

    fun create(image: MultipartFile): Image {
        val img = Image(image.originalFilename!!)
        save(img.id, image)
        return imageRepository.save(img)
    }

    fun save(id: String, image: MultipartFile) {
        val file = File(imageFolder, id)
        if (!file.exists()) File(imageFolder).mkdir()
        try {
            image.transferTo(file)
        } catch (e: Exception) {
            println(e)
        }
    }

}