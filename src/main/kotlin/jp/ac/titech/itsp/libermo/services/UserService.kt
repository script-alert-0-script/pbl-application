package jp.ac.titech.itsp.libermo.services

import javassist.NotFoundException
import jp.ac.titech.itsp.libermo.models.User
import jp.ac.titech.itsp.libermo.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken) =
        when (val user = token.principal) {
            is User -> user
            else -> null
        }

    fun create(id: String, name: String, displayName: String? = null) =
        userRepository.save(User(id, name, displayName ?: name))

    fun get(id: String): User {
        val user = userRepository.findById(id)
        if (user.isPresent) return user.get()
        throw NotFoundException("User(id=$id) is not found")
    }

    fun exists(id: String) = userRepository.existsById(id)

    fun me(): User {
        val user = SecurityContextHolder.getContext().authentication.principal
        if (user is User) {
            return get(user.id)
        }
        // TODO delete default user
        return get("default")
    }


}