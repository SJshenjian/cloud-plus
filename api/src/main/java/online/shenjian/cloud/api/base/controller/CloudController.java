package online.shenjian.cloud.api.base.controller;

import cloud.sfxs.cloud.client.cloud.CloudClient;
import cloud.sfxs.cloud.client.cloud.SystemClient;
import cloud.sfxs.cloud.client.cloud.dto.doge.DogeDto;
import cloud.sfxs.cloud.client.cloud.dto.doge.DogeQueryDto;
import cloud.sfxs.cloud.client.cloud.dto.user.PasswordDto;
import jakarta.annotation.Resource;
import online.shenjian.cloud.api.base.service.DogeService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/15
 */
@RestController
public class CloudController implements CloudClient {

    private final DogeService dogeService;

    @Resource
    private SystemClient systemClient;

    public CloudController(DogeService dogeService) {
        this.dogeService = dogeService;
    }

    @Override
    public List<DogeDto> getTop100DogeBalanceHistory(DogeQueryDto dogeQueryDto) {
        return dogeService.getTop100DogeBalanceHistory(dogeQueryDto);
    }
    @Override
    public void checkDogeBalance() {
        // 测试微服务间调用
        systemClient.updatePassword(new PasswordDto());
        dogeService.checkDogeBalance();
    }
}
