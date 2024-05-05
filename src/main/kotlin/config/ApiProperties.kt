package ar.org.schoolsync.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("route")
data class ApiProperties (
    val base: String
)
