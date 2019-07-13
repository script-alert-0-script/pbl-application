package jp.ac.titech.itsp.mercari.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "item")
data class Item(
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    val owner: User,

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
) {
    @JsonIgnore
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "public_room", nullable = false)
    val publicRoom: ChatRoom = ChatRoom(this)
}