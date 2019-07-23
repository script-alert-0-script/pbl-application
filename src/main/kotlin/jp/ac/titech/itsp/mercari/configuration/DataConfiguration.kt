package jp.ac.titech.itsp.mercari.configuration

import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class DataConfiguration(
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository
) {

    private val logger = LoggerFactory.getLogger(DataConfiguration::class.java)

    @Profile("dev")
    @Bean
    fun init(passwordEncoder: PasswordEncoder): InitializingBean = InitializingBean {
        // TODO delete default user
        userRepository.save(User("default", "default", passwordEncoder.encode("pass"), "default")).run {
            logger.debug("User saved $id:$password")
        }
        val userNames = arrayOf("Kazuha", "Yaya", "Yuki")
        val itemNames = arrayOf("微分積分", "線形代数", "力学")
        val users = userNames.map {
            userRepository.save(User(it, it, passwordEncoder.encode("pass"), it)).apply {
                logger.debug("User saved $id:$password")
            }
        }
        repeat(users.size) {
            itemNames.forEach { name ->
                itemRepository.save(Item(users[it], name))
            }
        }
    }

}