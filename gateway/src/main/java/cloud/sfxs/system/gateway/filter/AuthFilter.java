package cloud.sfxs.system.gateway.filter;

import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权过滤器
 *
 * @author shenjian
 * @since 2025/7/26
 **/
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Resource
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求头中的 Authorization（例如 JWT）
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 调用鉴权服务
        return webClientBuilder.build()
                .post()
                .uri("lb://system-service/auth/validate") // 使用 Nacos 服务发现
                .header("Authorization", authHeader)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                    Mono.error(new RuntimeException("Authentication failed: " + response.statusCode())))
                .bodyToMono(AuthResponse.class)
                .flatMap(authResponse -> {
                    // 将 X-user-id 和 X-role 添加到请求头
                    exchange.getRequest().mutate()
                            .header("X-user-id", authResponse.getUserId())
                            .header("X-role", authResponse.getRole())
                            .build();
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -100; // 优先级高于其他过滤器
    }

    // 鉴权服务返回的响应类
    @Data
    private static class AuthResponse {
        private String userId;
        private String role;
    }
}