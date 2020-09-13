package org.lemon.common.security.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.CacheConstant;
import org.lemon.common.core.constant.CommonConstant;
import org.lemon.common.core.constant.SecurityConstant;
import org.lemon.common.core.constant.enums.SystemTypeEnum;
import org.lemon.common.core.exception.BusinessException;
import org.lemon.common.core.util.R;
import org.lemon.common.security.service.LemonUserService;
import org.lemon.common.security.util.GbUser;
import org.lemon.user.api.entity.UserInfo;
import org.lemon.user.api.feign.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * APP用户信息获取
 *
 * @author Donald
 */
@Slf4j
@Component("APP")
public class LemonAppUserServiceImpl implements LemonUserService {
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public UserDetails info(String username) throws UsernameNotFoundException {
        Cache cache = cacheManager.getCache(CacheConstant.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (GbUser) cache.get(username).get();
        }
        R<UserInfo> result;
        if (Validator.isMobile(username)) {
            //手机号登录
            result = remoteUserService.getByMobile(username, SecurityConstant.FROM_IN);
        } else {
            throw new BusinessException("目前暂时只支持手机登陆!");
        }
        UserDetails userDetails = getUserDetails(result);
        cache.put(username, userDetails);
        return userDetails;
    }

    @Override
    public UserDetails socialInfo(String openId) throws UsernameNotFoundException {
        return getUserDetails(remoteUserService.getBySocial(openId, SecurityConstant.FROM_IN));
    }

    /**
     * 构建userdetails
     *
     * @param result
     * @return
     */
    private UserDetails getUserDetails(R<UserInfo> result) {
        if (null == result) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        if (null == result.getData()) {
            throw new BusinessException("该手机号未注册，请先注册!");
        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        UserInfo user = result.getData();
        boolean enabled = StrUtil.equals(user.getUserStatus().toString(), CommonConstant.STATUS_NORMAL);
        // 判断bcrypt加密还是md5加密
        String encrypFeat = StrUtil.isBlank(user.getSalt()) ? SecurityConstant.MD5 : SecurityConstant.BCRYPT;
        // 构造security用户
        return new User(user.getUsername(), user.getPassword(), user.getUserStatus() == 1, true, true, true, authorities);
    }
}
