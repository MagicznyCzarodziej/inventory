package pl.przemyslawpitus.inventory.common.config.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.przemyslawpitus.inventory.common.api.ErrorHandler

@Configuration
class ApiConfig {

    @Bean
    fun errorHandler() = ErrorHandler()
}