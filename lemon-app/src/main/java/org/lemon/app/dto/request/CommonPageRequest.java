package org.lemon.app.dto.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author ErvinWang
 */
@Data
public class CommonPageRequest {
    @TableField(exist = false)
    private Long pageNo = 1L;
    @TableField(exist = false)
    private Long pageSize = 10L;
}
