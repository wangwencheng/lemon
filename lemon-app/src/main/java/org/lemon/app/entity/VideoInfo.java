package org.lemon.app.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lemon.app.dto.request.CommonPageRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-11-02
 */
@Data
@TableName("video_info")
@EqualsAndHashCode(callSuper = false)
public class VideoInfo extends CommonPageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String videoUrl;

    private String videoName;

    private Integer videoType;

    private String videoSource;

    private Integer recStatus;

    private Long version;

    private String createBy;

    private LocalDateTime createTime;

    private String modifyBy;

    private LocalDateTime modifyTime;


}