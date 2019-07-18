package jp.ac.titech.itsp.mercari.services

import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.exceptions.ForbiddenException
import jp.ac.titech.itsp.mercari.exceptions.IllegalStateException
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.ItemState
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService {

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var userService: UserService

    fun create(name: String, owner: User) = itemRepository.save(Item(name, owner))

    fun get(id: Long): Item {
        val item = itemRepository.findById(id)
        if (item.isPresent) return item.get()
        throw NotFoundException("Item(id=$id) is not found")
    }

    fun getAll(): List<Item> = itemRepository.findAll()

    fun search(name: String): List<Item> = itemRepository.findByNameContaining(name)

    fun request(id: Long): Item {
        val item = get(id)
        val buyer = userService.me()
        if (item.owner.id == buyer.id) throw ForbiddenException("You are owner.")
        if (item.state != ItemState.AVAILABLE) throw IllegalStateException("Item state is not ${ItemState.AVAILABLE}")
        // TODO ban
        item.state = ItemState.PENDING
        item.buyer = buyer
        return itemRepository.save(item)
    }

    fun cancel(id: Long): Item {
        val item = get(id)
        val user = userService.me()
        if (item.buyer?.id != user.id && item.owner.id != user.id) throw ForbiddenException("You are not owner or buyer.")
        if (item.state != ItemState.PENDING) throw IllegalStateException("Item state is not ${ItemState.PENDING}")
        item.state = ItemState.AVAILABLE
        item.buyer = null
        return itemRepository.save(item)
    }

    fun allow(id: Long): Item {
        val item = get(id)
        val owner = userService.me()
        if (item.owner.id != owner.id) throw ForbiddenException("You are not owner.")
        if (item.state != ItemState.PENDING) throw IllegalStateException("Item state is not ${ItemState.PENDING}")
        item.state = ItemState.COMPLETED
        return itemRepository.save(item)
    }

}