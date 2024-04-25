package ar.org.schoolsync

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class Schoolsync

fun main(args: Array<String>) {
    runApplication<Schoolsync>(*args)
    println("Hello World!")
}

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