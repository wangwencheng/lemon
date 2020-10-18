package org.lemon.app.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {

    @RequestMapping("getBanner")
    public R getBanner() {
        List<Map<String, Object>> bannerList = new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("isShow",1);
        map.put("url","www.baidu.com");
        map.put("imageUrl","../../../assets/2c3968e33e2342feaa9fa3695b2e169e.png");
        map.put("comment","仅做展示");
        bannerList.add(map);
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("bannerList",bannerList);
        returnMap.put("productList",new ArrayList<>());
        return R.ok(returnMap);
    }
}
