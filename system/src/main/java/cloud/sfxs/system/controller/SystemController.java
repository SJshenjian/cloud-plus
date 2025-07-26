package cloud.sfxs.system.controller;

import cloud.sfxs.system.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import online.shenjian.cloud.client.cloud.SystemClient;
import online.shenjian.cloud.client.cloud.dto.UserDto;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigDto;
import online.shenjian.cloud.client.cloud.dto.system.config.SysConfigQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.module.ModuleTreeDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgInfoQueryDto;
import online.shenjian.cloud.client.cloud.dto.system.org.OrgTreeDto;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleDto;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleModuleDto;
import online.shenjian.cloud.client.cloud.dto.system.role.RoleQueryDto;
import online.shenjian.cloud.client.cloud.dto.user.PasswordDto;
import online.shenjian.cloud.client.cloud.dto.user.UserQueryDto;
import online.shenjian.cloud.client.common.ResponseVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 系统相关控制类
 *
 * @author shenjian
 * @since 2023/7/31
 */
@RestController
public class SystemController implements SystemClient {


    private final OrgService orgService;
    private final ModuleService moduleService;
    private final RoleService roleService;
    private final RoleModuleService roleModuleService;
    private final SysConfigService sysConfigService;
    private final FileService fileService;
    private final AuthService authService;
    private final UserService userService;

    public SystemController(OrgService orgService, ModuleService moduleService, RoleService roleService, RoleModuleService roleModuleService,
                            SysConfigService sysConfigService, FileService fileService, AuthService authService, UserService userService) {
        this.orgService = orgService;
        this.moduleService = moduleService;
        this.roleService = roleService;
        this.roleModuleService = roleModuleService;
        this.sysConfigService = sysConfigService;
        this.fileService = fileService;
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public ResponseVo login(UserDto userDto) {
        return userService.login(userDto.getAccount(), userDto.getPassword());
    }

    @Override
    public ResponseVo<String> updatePassword(PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto.getOriginPassword(), passwordDto.getNewPassword());
    }

    @Override
    public IPage<UserDto> listUser(UserQueryDto userQueryDto) {
        return userService.listUser(userQueryDto);
    }

    @Override
    public ResponseVo saveUser(UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @Override
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }

    @Override
    public ResponseVo updateUser(UserDto userInfoDto) {
        return userService.updateUser(userInfoDto);
    }

    @Override
    public ResponseVo resetPassword(String userId) {
        return userService.resetPassword(userId);
    }

    @Override
    public List<OrgTreeDto> initOrgInfoTree() {
        return orgService.initOrgInfoTree();
    }

    @Override
    public Boolean saveOrg(OrgInfoDto orgInfoDto) {
        return orgService.saveOrg(orgInfoDto);
    }

    @Override
    public void deleteOrg(String orgId) {
        orgService.deleteOrg(orgId);
    }

    @Override
    public IPage<OrgInfoDto> listOrg(OrgInfoQueryDto orgInfoQueryDto) {
        return orgService.listOrg(orgInfoQueryDto);
    }

    @Override
    public Boolean updateOrg(OrgInfoDto orgInfoDto) {
        return orgService.updateOrg(orgInfoDto);
    }

    @Override
    public List<ModuleTreeDto> initModuleInfoTree() {
        return moduleService.initModuleInfoTree();
    }

    @Override
    public Boolean saveModule(ModuleDto moduleDto) {
        return moduleService.saveModule(moduleDto);
    }

    @Override
    public void deleteModule(String moduleId) {
        moduleService.deleteModule(moduleId);
    }

    @Override
    public IPage<ModuleDto> listModule(ModuleQueryDto moduleQueryDto) {
        return moduleService.listModule(moduleQueryDto);
    }

    @Override
    public Boolean updateModule(ModuleDto moduleDto) {
        return moduleService.updateModule(moduleDto);
    }

    @Override
    public ResponseVo saveRole(RoleDto roleDto) {
        return roleService.saveRole(roleDto);
    }

    @Override
    public void deleteRole(String roleId) {
        roleService.deleteRole(roleId);
    }

    @Override
    public IPage<RoleDto> listRole(RoleQueryDto roleQueryDto) {
        return roleService.listRole(roleQueryDto);
    }

    @Override
    public Boolean updateRole(RoleDto roleDto) {
        return roleService.updateRole(roleDto);
    }

    @Override
    public Boolean authRole(RoleModuleDto roleModuleDto) {
        return roleModuleService.authRole(roleModuleDto);
    }

    @Override
    public String[] getRoleModule(String roleId) {
        return roleModuleService.getRoleModule(roleId);
    }

    @Override
    public Boolean saveConfig(SysConfigDto sysConfigDto) {
        return sysConfigService.saveConfig(sysConfigDto);
    }

    @Override
    public void deleteConfig(String id) {
        sysConfigService.deleteConfig(id);
    }

    @Override
    public IPage<SysConfigDto> listConfig(SysConfigQueryDto sysConfigQueryDto) {
        return sysConfigService.listConfig(sysConfigQueryDto);
    }

    @Override
    public Boolean updateConfig(SysConfigDto sysConfigDto) {
        return sysConfigService.updateConfig(sysConfigDto);
    }

    @Override
    public SysConfigDto getConfig(SysConfigDto sysConfigDto) {
        return sysConfigService.getConfig(sysConfigDto);
    }

    @Override
    public ResponseVo<Object> uploadFile(@RequestParam MultipartFile file) throws IOException {
        return fileService.upload(file);
    }

    @Override
    public ResponseVo<Object> authValidate(String headers) {
        return authService.validate(headers);
    }
}
