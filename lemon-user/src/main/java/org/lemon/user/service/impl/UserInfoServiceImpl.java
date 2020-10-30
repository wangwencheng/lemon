package org.lemon.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lemon.common.core.service.impl.BaseServiceImpl;
import org.lemon.common.core.util.R;
import org.lemon.common.security.util.SecurityUtil;
import org.lemon.user.api.dto.response.UserInfoDTO;
import org.lemon.user.api.entity.UserInfo;
import org.lemon.user.api.enums.ErrorCode;
import org.lemon.user.mapper.UserInfoMapper;
import org.lemon.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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

    @Override
    public R<UserInfoDTO> info() {
        User user = SecurityUtil.getUser();
        LambdaQueryWrapper<UserInfo> query = Wrappers.lambdaQuery();
        query.eq(UserInfo::getUsername,user.getUsername());
        UserInfo userInfo = userInfoMapper.selectOne(query);
        if (null == userInfo) {
            return R.failed(ErrorCode.USER_NOT_FIND);
        }
        UserInfoDTO dto = new UserInfoDTO();
        BeanUtil.copyProperties(userInfo, dto);
        return R.ok(dto);
    }
}
