package jp.ac.titech.itsp.libermo.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jp.ac.titech.itsp.libermo.configuration.firebase.FirebaseFilter
import jp.ac.titech.itsp.libermo.services.UserService
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
class WebSecurityConfiguration(
    private val userService: UserService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .logout().disable()
            .anonymous().disable()
            .exceptionHandling()
            .authenticationEntryPoint { _, response, exception ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.message)
            }
            .and()
            .addFilter(
                FirebaseFilter(userService).apply {
                    setAuthenticationManager(authenticationManager())
                }
            )
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST, "/api/**/*").authenticated()
            .mvcMatchers(HttpMethod.PUT, "/api/**/*").authenticated()
            .mvcMatchers(HttpMethod.PATCH, "/api/**/*").authenticated()
            .mvcMatchers(HttpMethod.DELETE, "/api/**/*").authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(
            PreAuthenticatedAuthenticationProvider().apply {
                setPreAuthenticatedUserDetailsService(userService)
            }
        )
    }

    @Bean
    fun initFirebaseApp() = InitializingBean {
        val credentials =
            GoogleCredentials.fromStream(ClassPathResource("credentials.json").inputStream)
        FirebaseApp.initializeApp(
            FirebaseOptions.Builder().setCredentials(credentials).build()
        )
    }

}
