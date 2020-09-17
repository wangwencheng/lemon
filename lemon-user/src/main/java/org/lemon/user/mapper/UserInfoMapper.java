package org.lemon.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lemon.user.api.entity.UserInfo;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-09-17
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
