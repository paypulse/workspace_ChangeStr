package com.example.workspace_changestr.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SpringSecurity {


    @Order(1)
    @Bean
    open fun apiFilterChain(http: HttpSecurity): SecurityFilterChain{
        http {
            securityMatcher("/api/**")
            authorizeHttpRequests {
                authorize(anyRequest, hasRole("ADMIN"))
            }
            httpBasic {  }
        }
        return http.build()
    }

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http {
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            formLogin {}
            httpBasic {  }
        }

        return http.build()

    }


    @Bean
    fun userDetailService():  UserDetailsService {
        val user:User.UserBuilder = User.withDefaultPasswordEncoder()
        val manager = InMemoryUserDetailsManager()
        manager.createUser(user.username("user").password("password").roles("USER").build())
        manager.createUser(user.username("admin").password("password").roles("USER","ADMIN").build())
        return manager
    }








}