package jp.ac.titech.itsp.mercari.repositories

import jp.ac.titech.itsp.mercari.models.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
    @Query("SELECT c FROM ChatRoom c JOIN FETCH c.chats WHERE c.id = (:id)")
    fun findByIdAndFetchChatsEagerly(@Param("id") id: Long): Optional<ChatRoom>
}