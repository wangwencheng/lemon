package org.lemon.app.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lemon.app.entity.VideoInfo;
import org.lemon.app.service.IVideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-11-02
 */
@RestController
@RequestMapping("video")
public class VideoInfoController {
    @Autowired
    private IVideoInfoService videoInfoService;

    @GetMapping("info")
    public R videoInfo(VideoInfo videoInfo) {
        Page<VideoInfo> page=new Page<>();
        page.setCurrent(videoInfo.getPageNo());
        page.setSize(videoInfo.getPageSize());
        return videoInfoService.selectPage(page,videoInfo);
    }
}
