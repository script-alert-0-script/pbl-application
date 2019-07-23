package jp.ac.titech.itsp.mercari.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @Column(name = "id", nullable = false)
    var id: String = "",

    @Column(name = "password", nullable = false)
    var password: String = ""
) {
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    @Column(name = "my_items", nullable = false)
    val myItems: MutableSet<Item> = mutableSetOf()

    @JsonIgnore
    @OneToMany(mappedBy = "buyer")
    @Column(name = "other_items", nullable = false)
    val otherItems: MutableSet<Item> = mutableSetOf()
}