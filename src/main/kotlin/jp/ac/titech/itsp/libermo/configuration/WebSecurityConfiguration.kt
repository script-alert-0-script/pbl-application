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
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val userService: UserService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf()
            .disable() // TODO
            .exceptionHandling()
            .authenticationEntryPoint { _, response, _ ->
                response.status = HttpServletResponse.SC_UNAUTHORIZED
            }
            .and()
            .addFilterAt(FirebaseFilter(userService), AbstractPreAuthenticatedProcessingFilter::class.java)
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST, "/api/**/*").authenticated()
            .mvcMatchers(HttpMethod.PUT, "/api/**/*").authenticated()
            .mvcMatchers(HttpMethod.DELETE, "/api/**/*").authenticated()
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
