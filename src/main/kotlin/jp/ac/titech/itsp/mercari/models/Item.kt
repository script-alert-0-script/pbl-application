package jp.ac.titech.itsp.mercari.models

import javax.persistence.*

@Entity
@Table(name = "item")
data class Item(
    @Column(name = "name", nullable = false)
    var name: String = "",

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    var owner: User = User(),

    @Column(name = "chats", nullable = false)
    @OneToMany
    var publicChats: MutableList<Chat> = mutableListOf(),

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
)
