package tis.helloapiversioning.version

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Configuration
class VersioningConfiguration {
    @Bean
    fun webRegistration(): WebMvcRegistrations {
        return object : WebMvcRegistrations {
            override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
                return CustomRequestMappingHandlerMapping()
            }
        }
    }
}
