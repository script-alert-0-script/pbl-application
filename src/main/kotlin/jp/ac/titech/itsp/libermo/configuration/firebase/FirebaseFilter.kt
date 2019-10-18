package jp.ac.titech.itsp.libermo.configuration.firebase

import com.google.firebase.auth.FirebaseAuth
import jp.ac.titech.itsp.libermo.models.User
import jp.ac.titech.itsp.libermo.services.UserService
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import java.util.*
import javax.servlet.http.HttpServletRequest

class FirebaseFilter(
    private val userService: UserService
) : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest) = authenticate(request)
    override fun getPreAuthenticatedCredentials(request: HttpServletRequest) = getToken(request)

    private fun authenticate(request: HttpServletRequest): User? {
        val auth = FirebaseAuth.getInstance()

        val token = try {
            auth.verifyIdToken(getToken(request))
        } catch (e: Exception) {
            logger.warn("Failed to verify token", e)
            return null
        }

        if (!validateDomain(token.email)) {
            // delete invalid user
            try {
                auth.deleteUser(token.uid)
            } catch (e: Exception) {
                logger.warn("Failed to delete invalid user", e)
            }

            // TODO: ドメインが違う場合のレスポンスとか
            return null
        }

        return if (!userService.exists(token.uid)) userService.create(token.uid, token.email)
        else userService.get(token.uid)
    }

    private fun validateDomain(email: String): Boolean {
        val mDomain = "@m.titech.ac.jp"
        return email.endsWith(mDomain)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val authorization = request.getHeader("Authorization")
        val bearerPrefix = "bearer "
        return if (authorization == null || !authorization.toLowerCase(Locale.ENGLISH).startsWith(bearerPrefix)) null
        else authorization.substring(bearerPrefix.length)
    }

}