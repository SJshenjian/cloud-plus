package online.shenjian.cloud.common.model;

import lombok.Data;

@Data
public class UserContext {

    private String userId;
    private String orgCode;

    // 构造函数
    public UserContext(String userId, String orgCode) {
        this.userId = userId;
        this.orgCode = orgCode;
    }
}