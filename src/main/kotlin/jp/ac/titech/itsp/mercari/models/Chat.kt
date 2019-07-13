package jp.ac.titech.itsp.mercari.models

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "chat")
data class Chat(
    @ManyToOne
    @JoinColumn(name = "chat_room", nullable = false)
    val chatRoom: ChatRoom,

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    val user: User,

    @Column(name = "message", nullable = false)
    var message: String = "",

    @Column(name = "date", nullable = false)
    var date: Date = Date(),

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
)
