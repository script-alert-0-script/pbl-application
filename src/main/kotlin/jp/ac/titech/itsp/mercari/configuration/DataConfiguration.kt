package jp.ac.titech.itsp.mercari.configuration

import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@Configuration
class DataConfiguration {

    private val logger = LoggerFactory.getLogger(DataConfiguration::class.java)

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var itemRepository: ItemRepository

    // TODO
    @Profile("dev")
    @Bean
    fun init(): InitializingBean = InitializingBean {
        userRepository.save(User("default", "pass")).run {
            logger.debug("User saved $id:$password")
        }
        repeat(10) {
            userRepository.save(User("user$it", "pass")).run {
                itemRepository.save(Item("item-name$it", this))
                logger.debug("User saved $id:$password")
            }
        }
    }

}