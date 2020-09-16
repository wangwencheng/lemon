package org.lemon.common.core.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 租户基础实体类
 * @author wwc
 */
@Data
public class TenantEntity<T extends Model<?>> extends BaseEntity<T> {
	@ApiModelProperty(value = "租户编号")
	private Integer tenantId;
}
