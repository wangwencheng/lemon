package org.lemon.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lemon.common.core.service.impl.BaseServiceImpl;
import org.lemon.common.core.util.R;
import org.lemon.user.api.entity.UserInfo;
import org.lemon.user.mapper.UserInfoMapper;
import org.lemon.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-09-17
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getByMobile(String mobile) {
        LambdaQueryWrapper<UserInfo> query = Wrappers.lambdaQuery();
        query.eq(UserInfo::getUsername, mobile);
        return userInfoMapper.selectOne(query);
    }
}
