package online.shenjian.cloud.api.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import online.shenjian.cloud.common.UserContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return (RequestTemplate template) -> {
            String token = UserContextHolder.getToken();
            if (token != null) {
                template.header("Authorization", token);
            }
        };
    }
}