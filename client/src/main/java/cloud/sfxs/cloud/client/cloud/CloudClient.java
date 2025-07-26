package cloud.sfxs.cloud.client.cloud;

import io.swagger.v3.oas.annotations.Operation;
import cloud.sfxs.cloud.client.cloud.dto.doge.DogeDto;
import cloud.sfxs.cloud.client.cloud.dto.doge.DogeQueryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "cloud", contextId = "cloud")
@Component
public interface CloudClient {

    @PostMapping(value = "/doge/getTop100DogeBalanceHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "余额历史监控", tags = "DOGE")
    List<DogeDto> getTop100DogeBalanceHistory(@RequestBody DogeQueryDto dogeQueryDto);

    @PostMapping(value = "/doge/checkDogeBalance", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "余额变化监控", tags = "DOGE")
    void checkDogeBalance();
}

