package jp.ac.titech.itsp.mercari.services

import jp.ac.titech.itsp.mercari.models.Chat
import jp.ac.titech.itsp.mercari.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService {
    @Autowired
    lateinit var itemService: ItemService

    fun create(message: String, user: User, itemId: Long): Chat{
        var item = itemService.get(itemId)
        var room = item.publicRoom
        val chat = Chat(message, user, Date(), room)
        room.add(chat)
        return chat
    }

    fun getAll(id: Long): List<Chat> = itemService.get(id).publicRoom.chats
}