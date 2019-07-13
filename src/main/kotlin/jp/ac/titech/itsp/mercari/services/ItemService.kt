package jp.ac.titech.itsp.mercari.services

import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService {

    @Autowired
    lateinit var itemRepository: ItemRepository

    fun create(owner: User, name: String) = itemRepository.save(Item(owner, name))

    fun get(id: Long): Item {
        val item = itemRepository.findById(id)
        if (item.isPresent) return item.get()
        throw NotFoundException("Item(id=$id) is not found")
    }

    fun getAll(): List<Item> = itemRepository.findAll()

    fun search(name: String): List<Item> = itemRepository.findByNameContaining(name)

}