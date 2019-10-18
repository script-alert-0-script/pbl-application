package jp.ac.titech.itsp.libermo.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = "",

    @Column(name = "name", nullable = false, unique = true)
    var name: String = "",

    @Column(name = "display_name", nullable = false)
    var displayName: String = ""
) : UserDetails {
    @JsonIgnore
    override fun getUsername() = name

    @JsonIgnore
    override fun getPassword() = ""

    @JsonIgnore
    override fun getAuthorities() = emptyList<GrantedAuthority>()

    @JsonIgnore
    override fun isEnabled() = true

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    @Column(name = "own_items", nullable = false)
    val ownItems: MutableSet<Item> = mutableSetOf()

    @JsonIgnore
    @OneToMany(mappedBy = "buyer")
    @Column(name = "buy_items", nullable = false)
    val buyItems: MutableSet<Item> = mutableSetOf()
}