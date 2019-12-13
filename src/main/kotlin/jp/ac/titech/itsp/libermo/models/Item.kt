package jp.ac.titech.itsp.libermo.models

import org.codehaus.jackson.annotate.JsonIgnore
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

    @JsonIgnore
    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "image")
    val image: Image? = null,

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
) {
    var imageURI
        get() = if (image != null) "/api/image/${image.id}" else "/assets/no-image.png"
        set(_) {}
}
