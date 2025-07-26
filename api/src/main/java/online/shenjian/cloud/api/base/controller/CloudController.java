package online.shenjian.cloud.api.base.controller;

import online.shenjian.cloud.api.base.service.DogeService;
import online.shenjian.cloud.client.cloud.CloudClient;
import online.shenjian.cloud.client.cloud.dto.doge.DogeDto;
import online.shenjian.cloud.client.cloud.dto.doge.DogeQueryDto;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shenjian
 * @since 2023/8/15
 */
@RestController
public class CloudController implements CloudClient {

    private final DogeService dogeService;

    public CloudController(DogeService dogeService) {
        this.dogeService = dogeService;
    }

    @Override
    public List<DogeDto> getTop100DogeBalanceHistory(DogeQueryDto dogeQueryDto) {
        return dogeService.getTop100DogeBalanceHistory(dogeQueryDto);
    }
    @Override
    public void checkDogeBalance() {
        dogeService.checkDogeBalance();
    }
}
