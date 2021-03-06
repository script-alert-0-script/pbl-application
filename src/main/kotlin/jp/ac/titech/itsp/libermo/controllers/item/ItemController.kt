package jp.ac.titech.itsp.libermo.controllers.item

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import javassist.NotFoundException
import jp.ac.titech.itsp.libermo.exceptions.ForbiddenException
import jp.ac.titech.itsp.libermo.exceptions.IllegalStateException
import jp.ac.titech.itsp.libermo.models.Item
import jp.ac.titech.itsp.libermo.services.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/item")
class ItemController(
    private val itemService: ItemService
) {

    @ApiOperation("Register an item")
    @PostMapping
    fun register(
        name: String,
        author: String,
        description: String,
        image: MultipartFile? = null
    ): ResponseEntity<Long> {
        if (image != null && !(image.contentType ?: "").matches(Regex("image/.*"))) return ResponseEntity.badRequest().build()
        val item = itemService.create(name, author, description, image)
        return ResponseEntity.ok(item.id)
    }

    @ApiOperation("Get an item by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Item not found")])
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<Item> {
        return try {
            ResponseEntity.ok(itemService.get(id))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @ApiOperation("Request to buy an item by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Item not found")])
    @PostMapping("/{id}/request")
    fun request(@PathVariable("id") id: Long): ResponseEntity<Item> {
        return try {
            ResponseEntity.ok(itemService.request(id))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().build()
        } catch (e: ForbiddenException) {
            ResponseEntity.badRequest().build()
        }
    }

    @ApiOperation("Refuse request to buy an item by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Item not found")])
    @PostMapping("/{id}/refuse")
    fun refuse(@PathVariable("id") id: Long): ResponseEntity<Item> {
        return try {
            ResponseEntity.ok(itemService.refuse(id))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().build()
        } catch (e: ForbiddenException) {
            ResponseEntity.badRequest().build()
        }
    }

    @ApiOperation("Allow request to buy an item by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Item not found")])
    @PostMapping("/{id}/allow")
    fun allow(@PathVariable("id") id: Long): ResponseEntity<Item> {
        return try {
            ResponseEntity.ok(itemService.allow(id))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().build()
        } catch (e: ForbiddenException) {
            ResponseEntity.badRequest().build()
        }
    }

    @ApiOperation("Get all items")
    @GetMapping
    fun getAll() = ResponseEntity.ok(itemService.getAll())

    @ApiOperation("Search items by name")
    @GetMapping("/search")
    fun search(@RequestParam name: String) = ResponseEntity.ok(itemService.search(name))

    @ApiOperation("Cancel exhibit an item by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Item not found")])
    @PostMapping("/{id}/cancel")
    fun cancel(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        return try {
            ResponseEntity.ok(itemService.cancel(id))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().build()
        } catch (e: ForbiddenException) {
            ResponseEntity.badRequest().build()
        }
    }

}
