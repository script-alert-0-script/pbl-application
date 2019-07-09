package jp.ac.titech.itsp.mercari.models

import javax.persistence.*

@Entity
@Table(name = "chat_room")
data class ChatRoom (
    @OneToOne
    val item: Item
) {
    @OneToMany
    var chats: MutableList<Chat> = mutableListOf()

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L

    fun add(chat: Chat) {
       chats.add(chat)
    }
}