package ar.org.schoolsync.config.security

import ar.org.schoolsync.config.ApiProperties
import ar.org.schoolsync.model.enums.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    private val apiProperties: ApiProperties
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        AntPathRequestMatcher("${apiProperties.base}/auth"),
                        AntPathRequestMatcher("${apiProperties.base}/auth/refresh"),
                        AntPathRequestMatcher("/error"),
                        AntPathRequestMatcher("/swagger-ui/**"),
                        AntPathRequestMatcher("/swagger-resources/**"),
                        AntPathRequestMatcher("/v3/api-docs/**"),
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST, "${apiProperties.base}/user")
                    .permitAll()
                    .requestMatchers("${apiProperties.base}/user**")
                    .hasRole(Role.ADMIN.name)
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}