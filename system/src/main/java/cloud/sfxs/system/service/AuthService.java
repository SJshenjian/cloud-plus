package cloud.sfxs.system.service;

import cloud.sfxs.cloud.client.common.ResponseVo;

/**
 * @author shenjian
 * @since 2025/7/26
 **/
public interface AuthService {

    /**
     * TOKEN 校验
     *
     * @param headers
     * @return
     */
    ResponseVo validate(String headers);
}
