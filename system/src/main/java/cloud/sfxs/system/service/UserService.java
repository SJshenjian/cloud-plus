package cloud.sfxs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cloud.sfxs.cloud.client.cloud.dto.UserDto;
import cloud.sfxs.cloud.client.cloud.dto.user.UserQueryDto;
import cloud.sfxs.cloud.client.common.ResponseVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenjian
 * @since 2023-08-25
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    ResponseVo login(String username, String password);

    ResponseVo<String> updatePassword(String originPassword, String newPassword);

    IPage<UserDto> listUser(UserQueryDto userQueryDto);

    ResponseVo saveUser(UserDto userDto);

    void deleteUser(String userId);

    ResponseVo updateUser(UserDto userDto);

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    ResponseVo resetPassword(String userId);
}
