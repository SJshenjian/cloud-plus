package cloud.sfxs.cloud.client.cloud.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {

    private Integer page;        // 当前页码
    private Integer size;        // 每页大小
    private Long total;      // 总记录数
    private List<T> data;    // 数据列表

    public PageVo(Integer page, Integer size, Long total, List<T> data) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.data = data;
    }
}