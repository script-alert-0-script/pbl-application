package jp.ac.titech.itsp.mercari.controllers.user

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import javassist.NotFoundException
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {

    @ApiOperation("Register a user")
    @PostMapping
    fun register(
        @RequestParam id: String, @RequestParam name: String, @RequestParam password: String, @RequestParam displayName: String? = null
    ): ResponseEntity<User> {
        val user = userService.create(id, name, password, displayName)
        return ResponseEntity.ok(user)
    }

    @ApiOperation("Get a user by id")
    @ApiResponses(value = [ApiResponse(code = 404, message = "User not found")])
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<User> {
        return try {
            ResponseEntity.ok(userService.get(id))
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        }
    }

    @ApiOperation("Get me")
    @ApiResponses(value = [ApiResponse(code = 404, message = "User not found")])
    @GetMapping("/me")
    fun me(): ResponseEntity<User> {
        return try {
            ResponseEntity.ok(userService.me())
        } catch (e: NotFoundException) {
            ResponseEntity.notFound().build()
        }
    }
}
