package online.shenjian.cloud.common;

import online.shenjian.cloud.common.model.UserContext;

/**
 * 存储用户上下文信息
 */
public class UserContextHolder {

    private static final ThreadLocal<UserContext> contextHolder = new ThreadLocal<>();

    public static void setContext(String userId, String orgCode) {
        contextHolder.set(new UserContext(userId, orgCode));
    }

    public static String getUserId() {
        UserContext context = contextHolder.get();
        if (context == null) {
            throw new IllegalStateException("User context not found");
        }
        return context.getUserId();
    }

    public static String getOrgCode() {
        UserContext context = contextHolder.get();
        if (context == null) {
            throw new IllegalStateException("User context not found");
        }
        return context.getOrgCode();
    }


    public static void clear() {
        contextHolder.remove();
    }
}