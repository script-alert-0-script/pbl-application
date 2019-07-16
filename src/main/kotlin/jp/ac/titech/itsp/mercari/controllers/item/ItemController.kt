package jp.ac.titech.itsp.mercari.controllers.item

import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.services.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.GetMapping
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.ApiOperation
import jp.ac.titech.itsp.mercari.exceptions.ForbiddenException
import jp.ac.titech.itsp.mercari.exceptions.IllegalStateException
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.services.UserService


@RestController
@RequestMapping("/api/item")
class ItemController {

    @Autowired
    lateinit var itemService: ItemService

    @Autowired
    lateinit var userService: UserService

    @ApiOperation("Register a item")
    @PostMapping
    fun register(@RequestParam name: String): ResponseEntity<Long> {
        val item = itemService.create(name, userService.me())
        return ResponseEntity.ok(item.id)
    }

    @ApiOperation("Get a item by id")
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
        }
    }

    @ApiOperation("Cancel request to buy an item by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Item not found")])
    @PostMapping("/{id}/cancel")
    fun cancel(@PathVariable("id") id: Long): ResponseEntity<Item> {
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

}
