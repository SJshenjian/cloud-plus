package cloud.sfxs.system.gateway.filter;

import cloud.sfxs.cloud.client.cloud.SystemClient;
import cloud.sfxs.cloud.client.cloud.dto.system.auth.ClaimsDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

/**
 * 网关鉴权过滤器
 *
 * @author shenjian
 * @since 2025/7/26
 **/
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Lazy // 延迟加载，避免循环依赖
    @Resource
    private SystemClient systemClient;

    private final WebClient webClient;

    public AuthFilter(WebClient.Builder webClientBuilder, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        // webClient 配置负载均衡和基础URL，并通过 lbFunction 实现负载均衡
        this.webClient = webClientBuilder
                .filter(lbFunction)
                .baseUrl("lb://system-service")
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        // Define paths to bypass
        List<String> publicPaths = Arrays.asList(
                "/system/login"
        );
        if (publicPaths.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange); // Skip authentication
        }
        // 获取请求头中的 Authorization（例如 JWT）
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 调用鉴权服务
        return webClient.get()
                .uri("/auth/validate")
                .header("Authorization", authHeader)
                .retrieve()
                .bodyToMono(ClaimsDto.class)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(claimsDto -> {
                    exchange.getRequest().mutate()
                            .header("X-user-id", claimsDto.getAccount())
                            .header("X-org-code", claimsDto.getOrgCode())
                            .header("X-role", String.join(",", claimsDto.getRoles()))
                            .build();
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> {
                    log.error("Error during authentication: ", e);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -100; // 优先级高于其他过滤器
    }
}