package ar.org.schoolsync.config

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun springShopOpenApi(): GroupedOpenApi {
        val paths = arrayOf("/api/**")
        return GroupedOpenApi.builder()
            .group("springdoc")
            .pathsToMatch(*paths)
            .build()
    }
}