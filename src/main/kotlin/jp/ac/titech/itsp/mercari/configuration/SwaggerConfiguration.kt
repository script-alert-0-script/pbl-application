package jp.ac.titech.itsp.mercari.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.builders.PathSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.UiConfigurationBuilder
import springfox.documentation.swagger.web.UiConfiguration




@Configuration
@EnableSwagger2
class SwaggerConfiguration {
    @Bean
    fun petApi(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(PathSelectors.ant("/api/**"))
        .build()
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo())

    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title("titech-mercari")
        .description("titech-mercari api")
        .version("0.0.1-SNAPSHOT")
        .build()

    @Bean
    fun uiConfig(): UiConfiguration = UiConfigurationBuilder.builder()
        .displayRequestDuration(true)
        .validatorUrl("")
        .build()
}