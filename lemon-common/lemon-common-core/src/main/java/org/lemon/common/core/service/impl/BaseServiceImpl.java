package org.lemon.common.core.service.impl;

import org.lemon.common.core.service.BaseService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 基础业务实现类
 * @author Donald
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
	//TODO 增加自定义方法 ex:幂等性
}
