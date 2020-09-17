package org.lemon.user.service;

import org.lemon.common.core.util.R;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lemon.user.api.entity.UserInfo;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-09-17
 */
public interface IUserInfoService{

    UserInfo getByMobile(String mobile);
}
