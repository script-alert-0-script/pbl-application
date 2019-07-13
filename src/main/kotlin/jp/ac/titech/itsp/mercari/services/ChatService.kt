package jp.ac.titech.itsp.mercari.services

import jp.ac.titech.itsp.mercari.models.Chat
import jp.ac.titech.itsp.mercari.models.ChatRoom
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ChatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChatService {

    @Autowired
    lateinit var itemService: ItemService

    @Autowired
    lateinit var chatRepository: ChatRepository

    @Autowired
    lateinit var chatRoomService: ChatRoomService

    fun create(chatRoom: ChatRoom, user: User, message: String): Chat =
        chatRepository.save(Chat(chatRoom, user, message))

    fun createByItemId(itemId: Long, user: User, message: String): Chat =
        create(itemService.get(itemId).publicRoom, user, message)

    fun getAll(chatRoom: ChatRoom): List<Chat> = chatRoomService.get(chatRoom.id, true).chats

    fun getAllByItemId(itemId: Long): List<Chat> = getAll(itemService.get(itemId).publicRoom)

}