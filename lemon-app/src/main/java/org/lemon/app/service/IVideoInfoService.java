package org.lemon.app.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lemon.app.entity.VideoInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-11-02
 */
public interface IVideoInfoService extends IService<VideoInfo> {

    R selectPage(VideoInfo videoInfo);
}
