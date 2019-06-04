package jp.ac.titech.itsp.mercari.controllers.item

import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.services.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/item")
class ItemController {

    @Autowired
    lateinit var itemService: ItemService

    @PostMapping
    fun register(@RequestParam name: String, @RequestParam user: String): ResponseEntity<Long> {
        val item = itemService.create(name, user)
        return ResponseEntity.ok(item.id)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<Item> {
        return try {
            ResponseEntity.ok(itemService.get(id))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build<Item>()
        }
    }

    @GetMapping
    fun getAll() = ResponseEntity.ok(itemService.getAll())

}
