package cloud.sfxs.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cloud.sfxs.cloud.client.cloud.dto.system.role.RoleModuleDto;
import cloud.sfxs.system.model.RoleModule;

/**
 * @author shenjian
 * @since 2023/8/7
 */
public interface RoleModuleService extends IService<RoleModule> {

    /**
     * 角色授权
     *
     * @param roleModuleDto
     * @return
     */
    Boolean authRole(RoleModuleDto roleModuleDto);

    /**
     * 获取角色菜单信息
     *
     * @param roleId
     * @return
     */
    String[] getRoleModule(String roleId);
}
