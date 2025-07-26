package cloud.sfxs.system.service.impl;

import cloud.sfxs.system.config.model.Claims;
import cloud.sfxs.system.mapper.UserMapper;
import cloud.sfxs.system.model.User;
import cloud.sfxs.system.service.AuthService;
import cloud.sfxs.system.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import online.shenjian.cloud.client.cloud.dto.system.auth.ClaimsDto;
import online.shenjian.cloud.client.common.ResponseCode;
import online.shenjian.cloud.client.common.ResponseVo;
import online.shenjian.cloud.common.enums.Constant;
import online.shenjian.cloud.common.utils.CommonDtoUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 授权验证服务
 *
 * @author shenjian
 * @since 2025/7/26
 **/
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    public AuthServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public ResponseVo validate(String token) {
        Claims claims = TokenUtils.getClaimsFromToken(token);
        if (claims == null) {
            return  ResponseVo.message(ResponseCode.UN_AUTHORIZED);
        }
        if (claims.getExp() < new Date().getTime()) {
            return ResponseVo.message(ResponseCode.LICENSE_EXPIRED);
        }
        // 需要数据库验证的场景, 我们先仅仅简单查询数据库
        //   用户状态动态变化: 用户被禁用、删除或注销; 用户角色或权限发生变化
        //   敏感操作：对于高安全性的操作（如转账、修改密码），需要确保用户状态实时有效；可能需要检查用户是否被列入黑名单或存在其他限制
        //   会话管理: 如果系统需要支持强制登出（如用户在其他设备登录），需要在数据库中维护有效的会话状态或 JWT 黑名单
        //   合规性要求: 某些行业（如金融、医疗）可能要求实时验证用户状态以满足审计或合规需求
        //   外部依赖数据: 如果鉴权需要额外信息（如用户的组织信息、订阅状态），而这些信息未包含在 JWT Payload 中，则需查询数据库
        // 无需数据库验证的场景
        //    用户信息静态且不变：如果 Payload 中的信息（如 userId、role）在 JWT 有效期内不会变化（例如，用户角色短期内不更改）。
        //    性能优先：避免数据库查询可以显著提高系统性能，尤其在高并发场景下。
        //    分布式系统：在微服务架构中，网关或服务间通过 JWT 传递用户信息，依赖 JWT 的自包含性减少跨服务数据库调用。
        //    简单鉴权：只需要验证用户身份和基本权限（如 role），无需检查额外状态
        String account = claims.getAccount();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        queryWrapper.eq("del_flag", Constant.YesOrNo.NO.val());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("登录用户：{} 不存在", account);
            return ResponseVo.message(ResponseCode.UN_AUTHORIZED);
        }
        ClaimsDto claimsDto = CommonDtoUtils.transform(claims, ClaimsDto.class);
        return ResponseVo.success(claimsDto);
    }
}
