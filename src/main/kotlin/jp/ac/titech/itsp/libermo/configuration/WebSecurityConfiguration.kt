package jp.ac.titech.itsp.libermo.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jp.ac.titech.itsp.libermo.configuration.firebase.FirebaseFilter
import jp.ac.titech.itsp.libermo.services.UserService
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val userService: UserService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint { _, response, _ ->
                response.status = HttpServletResponse.SC_UNAUTHORIZED
            }
            .and()
            .addFilter(preAuthenticatedProcessingFilter())
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST, "/api/**/*").authenticated()
            .mvcMatchers(HttpMethod.PUT, "/api/**/*").authenticated()
            .mvcMatchers(HttpMethod.DELETE, "/api/**/*").authenticated()
        http.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(
            preAuthenticatedAuthenticationProvider()
        )
    }

    @Bean
    fun preAuthenticatedProcessingFilter() =
        FirebaseFilter(userService).apply {
            setAuthenticationManager(authenticationManager())
        }

    @Bean
    fun preAuthenticatedAuthenticationProvider() =
        PreAuthenticatedAuthenticationProvider().apply {
            setPreAuthenticatedUserDetailsService(userService)
            setUserDetailsChecker(AccountStatusUserDetailsChecker())
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
