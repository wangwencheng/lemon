package org.lemon.app.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-10-28
 */
@Data
@TableName("app_button")
@EqualsAndHashCode(callSuper = false)
public class AppButton implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * banner名称
     */
    private String buttonName;

    /**
     * banner跳转链接
     */
    private String buttonType;

    /**
     * 是否内部banner
     */
    private Integer buttonInner;

    /**
     * banner状态  1、启用 0、禁用
     */
    private Integer status;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifyBy;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    @TableField(exist = false)
    private Integer fetchSize;
}