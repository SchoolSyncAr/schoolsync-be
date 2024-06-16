package ar.org.schoolsync.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
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

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                Components().addSecuritySchemes(
                    "bearerAuth", SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            )
    }
}