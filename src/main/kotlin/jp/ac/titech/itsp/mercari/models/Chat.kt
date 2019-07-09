package jp.ac.titech.itsp.mercari.models

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "chat")
data class Chat(
    @Column(name = "message", nullable = false)
    var message: String = "",

    @Column(name = "user", nullable = false)
    var user: User,

    @Column(name = "date", nullable = false)
    var date: Date = Date(),

    @ManyToOne
    var chatRoom: ChatRoom,

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
)
