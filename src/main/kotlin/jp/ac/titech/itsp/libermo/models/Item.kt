package jp.ac.titech.itsp.libermo.models

import javax.persistence.*

@Entity
@Table(name = "item")
data class Item(
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    val owner: User,

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "author", nullable = false)
    var author: String = "",

    @Column(name = "description", nullable = false)
    var description: String = "",

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "image")
    var image: Image? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    var state: ItemState = ItemState.AVAILABLE,

    @ManyToOne
    @JoinColumn(name = "buyer")
    var buyer: User? = null,

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    val id: Long = 0L
)
