package org.lemon.common.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 租户VO
 * @author Donald
 */
@Data
public class TenantVo implements Serializable {

	private Integer tenantId;

	private String tenantName;

	private String status;

	private String domain;
}
