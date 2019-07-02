package jp.ac.titech.itsp.mercari.services

import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    override fun loadUserByUsername(id: String): UserDetails {
        val user = get(id)
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.id)
            .password(user.password)
            .authorities("USER") // TODO permissions
            .build()
    }

    fun create(id: String, password: String) = userRepository.save(User(id, passwordEncoder.encode(password)))

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