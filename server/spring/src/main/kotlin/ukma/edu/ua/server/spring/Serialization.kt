package ukma.edu.ua.server.spring

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ukma.edu.ua.shared.data.Serialization

@Configuration
class Serialization : WebMvcConfigurer {

    @Bean
    fun json(): Json = Serialization.json
}