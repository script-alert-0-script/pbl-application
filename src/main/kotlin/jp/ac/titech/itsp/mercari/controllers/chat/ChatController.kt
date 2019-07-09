package jp.ac.titech.itsp.mercari.controllers.chat

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.Chat
import jp.ac.titech.itsp.mercari.services.ChatService
import jp.ac.titech.itsp.mercari.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/chat")
class ChatController {

    @Autowired
    lateinit var chatService: ChatService

    @Autowired
    lateinit var userService: UserService

    @ApiOperation("Send public chat message")
    @ApiResponses(value = [ApiResponse(code = 404, message = "Item not found")])
    @PostMapping("/{itemId}")
    fun send(@PathVariable("itemId") itemId: Long, @RequestParam message: String): ResponseEntity<Long> {
        val user = userService.me()
        return try {
            val chat = chatService.create(message, user, itemId)
            ResponseEntity.ok(chat.id)
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @ApiOperation("Get all public chat message for itemId")
    @GetMapping("/{itemId}")
    fun getAll(@PathVariable("itemId") itemId: Long): ResponseEntity<List<Chat>>{
        return try {
            ResponseEntity.ok(chatService.getAll(itemId))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        }
    }
}
