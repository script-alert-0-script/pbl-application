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

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    var state: ItemState = ItemState.AVAILABLE,

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
)
