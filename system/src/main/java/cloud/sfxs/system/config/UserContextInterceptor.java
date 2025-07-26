package cloud.sfxs.system.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.shenjian.cloud.common.UserContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class UserContextInterceptor implements HandlerInterceptor {

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/login/**"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();

        // 检查是否为放行路径
        if (isExcludedPath(requestPath)) {
            return true; // 放行，直接通过
        }

        String userId = request.getHeader("X-user-id");
        String orgCode = request.getHeader("X-org-code");
        // 验证必要字段
        if (userId == null || orgCode == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 存储到上下文
        UserContextHolder.setContext(userId, orgCode);
        return true;
    }

    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(excludedPath ->
                path.matches(excludedPath.replace("/**", "(/.*)?")));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear(); // 清理 ThreadLocal
    }
}