package jp.ac.titech.itsp.libermo.services

import javassist.NotFoundException
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
        val img = Image(image.originalFilename!!, image.contentType!!)
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

    fun get(id: String): Image {
        val optional = imageRepository.findById(id)
        if (optional.isPresent) return optional.get()
        throw NotFoundException("Image(id=$id) is not found")
    }

}