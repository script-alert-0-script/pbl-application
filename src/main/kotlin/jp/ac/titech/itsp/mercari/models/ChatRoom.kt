package jp.ac.titech.itsp.mercari.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "chat_room")
data class ChatRoom(
    @OneToOne
    val item: Item,

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
) {
    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL])
    @Column(name = "chats", nullable = false)
    val chats: MutableList<Chat> = mutableListOf()
}