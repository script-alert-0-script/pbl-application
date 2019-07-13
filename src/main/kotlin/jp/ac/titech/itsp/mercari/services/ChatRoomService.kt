package jp.ac.titech.itsp.mercari.services

import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.Chat
import jp.ac.titech.itsp.mercari.models.ChatRoom
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ChatRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatRoomService {

    @Autowired
    lateinit var chatRoomRepository: ChatRoomRepository

    fun get(id: Long, eager: Boolean = false): ChatRoom {
        val chatRoom =
            if (eager) chatRoomRepository.findByIdAndFetchChatsEagerly(id) else chatRoomRepository.findById(id)
        if (chatRoom.isPresent) return chatRoom.get()
        throw NotFoundException("ChatRoom(id=$id) is not found")
    }

}