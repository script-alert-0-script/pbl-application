package jp.ac.titech.itsp.libermo.repositories

import jp.ac.titech.itsp.libermo.models.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {
    fun findByNameContaining(name: String): List<Item>
}
