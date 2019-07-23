package jp.ac.titech.itsp.mercari.services

import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val user = get(id)
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.id)
            .password(user.password)
            .authorities("USER") // TODO permissions
            .build()
    }

    fun create(id: String, name: String, password: String) =
        userRepository.save(User(id, name, passwordEncoder.encode(password)))

    fun get(id: String): User {
        val user = userRepository.findById(id)
        if (user.isPresent) return user.get()
        throw NotFoundException("User(id=$id) is not found")
    }

    fun me(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is org.springframework.security.core.userdetails.User) {
            return get(principal.username)
        }
        // TODO delete default user
        return get("default")
    }


}