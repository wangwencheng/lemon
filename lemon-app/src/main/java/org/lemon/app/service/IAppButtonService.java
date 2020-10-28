package org.lemon.app.service;

import org.lemon.app.entity.AppButton;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-10-28
 */
public interface IAppButtonService extends IService<AppButton> {

    List<AppButton> getButtonList(AppButton appButton);
}
