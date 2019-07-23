package jp.ac.titech.itsp.mercari.repositories

import jp.ac.titech.itsp.mercari.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String>
