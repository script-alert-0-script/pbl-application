package jp.ac.titech.itsp.libermo.controllers.image

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import javassist.NotFoundException
import jp.ac.titech.itsp.libermo.services.ImageService
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.support.ServletContextResource
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import javax.servlet.ServletContext

@RestController
@RequestMapping("/api/image")
class ImageController(
    private val imageService: ImageService,
    private val servletContext: ServletContext
) {

    @ApiOperation("Get an image by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Image not found")])
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<Resource> {
        return try {
            val image = imageService.get(id)
            val resource = InputStreamResource(
                BufferedInputStream(FileInputStream(File("${imageService.imageFolder}/${image.id}")))
            )
            ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(image.contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, """attachment; filename="${image.name}"""")
                .body(resource)
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

}