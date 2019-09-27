package jp.ac.titech.itsp.libermo.repositories

import jp.ac.titech.itsp.libermo.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String>
