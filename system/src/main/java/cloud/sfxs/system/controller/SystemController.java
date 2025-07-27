package cloud.sfxs.system.controller;

import cloud.sfxs.cloud.client.cloud.IPage;
import cloud.sfxs.cloud.client.cloud.SystemClient;
import cloud.sfxs.cloud.client.cloud.dto.PageVo;
import cloud.sfxs.cloud.client.cloud.dto.UserDto;
import cloud.sfxs.cloud.client.cloud.dto.system.auth.ClaimsDto;
import cloud.sfxs.cloud.client.cloud.dto.system.config.SysConfigDto;
import cloud.sfxs.cloud.client.cloud.dto.system.config.SysConfigQueryDto;
import cloud.sfxs.cloud.client.cloud.dto.system.module.ModuleDto;
import cloud.sfxs.cloud.client.cloud.dto.system.module.ModuleQueryDto;
import cloud.sfxs.cloud.client.cloud.dto.system.module.ModuleTreeDto;
import cloud.sfxs.cloud.client.cloud.dto.system.org.OrgInfoDto;
import cloud.sfxs.cloud.client.cloud.dto.system.org.OrgInfoQueryDto;
import cloud.sfxs.cloud.client.cloud.dto.system.org.OrgTreeDto;
import cloud.sfxs.cloud.client.cloud.dto.system.role.RoleDto;
import cloud.sfxs.cloud.client.cloud.dto.system.role.RoleModuleDto;
import cloud.sfxs.cloud.client.cloud.dto.system.role.RoleQueryDto;
import cloud.sfxs.cloud.client.cloud.dto.user.PasswordDto;
import cloud.sfxs.cloud.client.cloud.dto.user.UserQueryDto;
import cloud.sfxs.cloud.client.common.ResponseVo;
import cloud.sfxs.system.service.*;
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
    public PageVo<UserDto> listUser(UserQueryDto userQueryDto) {
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
        //return orgService.listOrg(orgInfoQueryDto);
        return null;
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
        //return moduleService.listModule(moduleQueryDto);
        return null;
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
        //return roleService.listRole(roleQueryDto);
        return null;
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
        //return sysConfigService.listConfig(sysConfigQueryDto);
        return null;
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
    public ClaimsDto authValidate(String headers) {
        return authService.validate(headers);
    }
}
