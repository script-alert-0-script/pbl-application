package jp.ac.titech.itsp.mercari.repositories

import jp.ac.titech.itsp.mercari.models.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemRepository : JpaRepository<Item, Long> {
    fun findByNameContaining(name: String): List<Item>
}
