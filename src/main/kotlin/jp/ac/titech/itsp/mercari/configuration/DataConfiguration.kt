package jp.ac.titech.itsp.mercari.configuration

import jp.ac.titech.itsp.mercari.models.Chat
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ChatRepository
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class DataConfiguration {

    private val logger = LoggerFactory.getLogger(DataConfiguration::class.java)

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var itemRepository: ItemRepository
    @Autowired
    lateinit var chatRepository: ChatRepository

    // TODO
    @Profile("dev")
    @Bean
    fun init(passwordEncoder: PasswordEncoder): InitializingBean = InitializingBean {
        userRepository.save(User("default", passwordEncoder.encode("pass"))).run {
            logger.debug("User saved $id:$password")
        }
        repeat(10) {
            userRepository.save(User("user$it", passwordEncoder.encode("pass"))).run {
                val item = itemRepository.save(Item(this, "item-name$it"))
                chatRepository.save(Chat(item.publicRoom, this, "message"))
                logger.debug("User saved $id:$password")
            }
        }
    }

}