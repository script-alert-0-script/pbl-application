package jp.ac.titech.itsp.mercari.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "item")
data class Item(
    @Column(name = "name", nullable = false)
    var name: String = "",

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    var owner: User = User(),

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
) {
    @JsonIgnore
    @OneToOne
    @Column(name = "public_room", nullable = false)
    var publicRoom: ChatRoom = ChatRoom(this)
}