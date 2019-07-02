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
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @Column(name = "items", nullable = false)
    val items: MutableSet<Item> = mutableSetOf()
}