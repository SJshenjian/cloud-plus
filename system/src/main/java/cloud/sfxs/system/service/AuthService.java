package cloud.sfxs.system.service;

import cloud.sfxs.cloud.client.cloud.dto.system.auth.ClaimsDto;

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
    ClaimsDto validate(String headers);
}
