package org.lemon.common.data.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.lemon.common.security.util.SecurityUtil;

import java.util.Date;
import java.util.Optional;

/**
 * 自定义填充公共字段
 *
 * @author Donald
 */
public class GbMetaObjectHandler implements MetaObjectHandler {
	private final static String CREATE_BY = "createBy";
	private final static String CREATE_TIME = "createTime";
	private final static String MODIFY_BY = "modifyBy";
	private final static String MODIFY_TIME = "modifyTime";

	/**
	 * 插入填充，字段为空自动填充
	 *
	 * @param metaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		Object createdBy = getFieldValByName(CREATE_BY, metaObject);
		Object createdTime = getFieldValByName(CREATE_TIME, metaObject);
		Object modifyBy = getFieldValByName(MODIFY_BY, metaObject);
		Object modifyTime = getFieldValByName(MODIFY_TIME, metaObject);
		Long userNo = Optional.ofNullable(SecurityUtil.getUser()).map(gbUser -> gbUser.getUserNo()).orElseGet(() -> Long.valueOf(0));

		if (createdBy == null) {
			setFieldValByName(CREATE_BY, userNo, metaObject);
		}

		if (createdTime == null) {
			setFieldValByName(CREATE_TIME, new Date(), metaObject);
		}

		if (modifyBy == null) {
			setFieldValByName(MODIFY_BY, userNo, metaObject);
		}

		if (modifyTime == null) {
			setFieldValByName(MODIFY_TIME, new Date(), metaObject);
		}

	}

	/**
	 * 更新填充
	 *
	 * @param metaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		setFieldValByName(MODIFY_TIME, new Date(), metaObject);
		setFieldValByName(MODIFY_BY, Optional.ofNullable(SecurityUtil.getUser()).map(gbUser -> gbUser.getUserNo()).orElseGet(() -> Long.valueOf(0)), metaObject);
	}
}
