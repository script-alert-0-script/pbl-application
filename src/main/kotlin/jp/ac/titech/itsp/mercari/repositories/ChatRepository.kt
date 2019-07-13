package jp.ac.titech.itsp.mercari.repositories

import jp.ac.titech.itsp.mercari.models.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : JpaRepository<Chat, Long>