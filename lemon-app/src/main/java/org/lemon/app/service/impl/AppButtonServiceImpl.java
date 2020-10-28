package org.lemon.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lemon.app.entity.AppButton;
import org.lemon.app.enums.StatusEnum;
import org.lemon.app.mapper.AppButtonMapper;
import org.lemon.app.service.IAppButtonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-10-28
 */
@Service
public class AppButtonServiceImpl extends ServiceImpl<AppButtonMapper, AppButton> implements IAppButtonService {

    @Autowired
    private AppButtonMapper appButtonMapper;

    @Override
    public List<AppButton> getButtonList(AppButton appButton) {
        LambdaQueryWrapper<AppButton> query = Wrappers.lambdaQuery();
        query.eq(AppButton::getStatus, StatusEnum.ENABLE.getCode());
        query.last("limit " +appButton.getFetchSize());
        return appButtonMapper.selectList(query);
    }
}
