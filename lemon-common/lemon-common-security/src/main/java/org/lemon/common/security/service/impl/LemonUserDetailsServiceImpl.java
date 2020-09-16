package org.lemon.common.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.enums.SystemTypeEnum;
import org.lemon.common.security.service.LemonUserDetailsService;
import org.lemon.common.security.service.LemonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户详细信息
 *
 * @author wwc
 */
@Slf4j
@Service(value = "LemonUserDetailsService")
public class LemonUserDetailsServiceImpl implements LemonUserDetailsService {
    @Autowired
    private Map<String, LemonUserService> userServiceMap;

    /**
     * 用户密码登录
     *
     * @param inStr
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String inStr) {
        String type = "APP";
  //      String username = StrUtil.subAfter(inStr, StringPool.AT, false);
   // 		type = Optional.ofNullable(type).orElseGet(() -> SystemTypeEnum.APP.getType());
   //     type = SystemTypeEnum.PC.getType().equals(type) ? SystemTypeEnum.APP.getType() : type;
        LemonUserService service = userServiceMap.get(type);
        if (null == service) {
            throw new UsernameNotFoundException("缺少必要的参数");
        }
        return service.info(inStr);
    }


    /**
     * 手机验证码登录/社交登录
     *
     * @param openId
     * @param type
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserBySocial(String openId, String type) throws UsernameNotFoundException {
        String from = StrUtil.subBefore(openId, StringPool.AT, false);
        String acc = StrUtil.subAfter(openId, StringPool.AT, false);
        if (StrUtil.isEmpty(from) || !userServiceMap.containsKey(from)) {
            throw new UsernameNotFoundException("缺少必要的参数");
        }
        return userServiceMap.get(from).socialInfo(type + StringPool.AT + acc);
    }
}
