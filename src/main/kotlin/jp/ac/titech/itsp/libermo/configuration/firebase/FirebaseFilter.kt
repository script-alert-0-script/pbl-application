package jp.ac.titech.itsp.libermo.configuration.firebase

import com.google.firebase.auth.FirebaseAuth
import jp.ac.titech.itsp.libermo.models.User
import jp.ac.titech.itsp.libermo.services.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FirebaseFilter(
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val user = authenticate(request)
        if (user != null) {
            SecurityContextHolder.getContext().authentication =
                PreAuthenticatedAuthenticationToken(
                    user, null
                )
        }
        filterChain.doFilter(request, response)
    }

    private fun authenticate(request: HttpServletRequest): User? {
        val token = try {
            FirebaseAuth.getInstance().verifyIdToken(getToken(request))
        } catch (e: Exception) {
            return null
        }

        if (!validateDomain(token.email))
        {
            // delete invalid user
            try {
                FirebaseAuth.getInstance().deleteUser(token.uid)
            } catch (e: Exception) {
                // pass
            }

            // TODO: ドメインが違う場合のレスポンスとか
            return null
        }

        if (!userService.exists(token.uid)) {
            userService.create(token.uid, token.name)
        }
        return userService.get(token.uid)
    }

    private fun validateDomain(email: String): Boolean {
        val mDomain = "@m.titech.ac.jp"
        return email.endsWith(mDomain)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        return if (token == null || !token.startsWith("Bearer ")) null
        else token.substring("Bearer ".length)
    }

}