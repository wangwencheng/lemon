package org.lemon.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.lemon.app.entity.VideoInfo;
import org.lemon.app.enums.RecStatusEnum;
import org.lemon.app.mapper.VideoInfoMapper;
import org.lemon.app.service.IVideoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-11-02
 */
@Service
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements IVideoInfoService {

    @Autowired
    private VideoInfoMapper videoInfoMapper;

    @Override
    public R selectPage(Page<VideoInfo> page, VideoInfo videoInfo) {
        LambdaQueryWrapper<VideoInfo> query = Wrappers.lambdaQuery();
        query.eq(VideoInfo::getRecStatus, RecStatusEnum.UNDELETED.getCode());
        query.eq(Objects.nonNull(videoInfo.getVideoType()), VideoInfo::getVideoType, videoInfo.getVideoType());
        query.like(!Strings.isNullOrEmpty(videoInfo.getVideoName()), VideoInfo::getVideoName, videoInfo.getVideoName());
        return R.ok(videoInfoMapper.selectPage(page, query));
    }
}
