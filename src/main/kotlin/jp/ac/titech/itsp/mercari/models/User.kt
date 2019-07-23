package jp.ac.titech.itsp.mercari.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = "",

    @Column(name = "name", nullable = false, unique = true)
    val name: String = "",

    // TODO Authenticate on Firebase
    @JsonIgnore
    @Column(name = "password", nullable = false)
    var password: String = ""
) {
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    @Column(name = "own_items", nullable = false)
    val ownItems: MutableSet<Item> = mutableSetOf()

    @JsonIgnore
    @OneToMany(mappedBy = "buyer")
    @Column(name = "buy_items", nullable = false)
    val buyItems: MutableSet<Item> = mutableSetOf()
}