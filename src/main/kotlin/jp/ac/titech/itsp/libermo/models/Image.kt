package jp.ac.titech.itsp.libermo.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "image")
data class Image(
    @Column(name = "name", nullable = false)
    val name: String = "",

    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString()
) {
    @JsonIgnore
    @OneToOne(mappedBy = "image")
    var item: Item? = null
}