package cloud.sfxs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cloud.sfxs.cloud.client.common.ResponseVo;
import cloud.sfxs.cloud.client.cloud.dto.system.role.RoleDto;
import cloud.sfxs.cloud.client.cloud.dto.system.role.RoleQueryDto;

/**
 * @author shenjian
 * @since 2023/8/7
 */
public interface RoleService {
    
    IPage<RoleDto> listRole(RoleQueryDto roleQueryDto);

    ResponseVo saveRole(RoleDto roleDto);

    void deleteRole(String roleId);

    Boolean updateRole(RoleDto roleDto);
}
