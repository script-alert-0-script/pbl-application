package jp.ac.titech.itsp.libermo.services

import javassist.NotFoundException
import jp.ac.titech.itsp.libermo.exceptions.ForbiddenException
import jp.ac.titech.itsp.libermo.exceptions.IllegalStateException
import jp.ac.titech.itsp.libermo.models.Item
import jp.ac.titech.itsp.libermo.models.ItemState
import jp.ac.titech.itsp.libermo.repositories.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val userService: UserService
) {

    fun create(name: String, author: String, description: String)
            = itemRepository.save(Item(userService.me(), name, author, description))

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

    fun refuse(id: Long): Item {
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

    fun cancel(id: Long) {
        val item = get(id)
        val owner = userService.me()
        if (item.owner.id != owner.id) throw ForbiddenException("You are not owner.")
        itemRepository.deleteById(id)
    }

}