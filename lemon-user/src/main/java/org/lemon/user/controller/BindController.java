package org.lemon.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.lemon.common.core.constant.enums.ErrorCodeEnum;
import org.lemon.common.core.util.R;
import org.lemon.common.security.annotation.Inner;
import org.lemon.user.api.dto.request.BindDTO;
import org.lemon.user.service.BindService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/")
@Api(value = "bind", tags = "绑定模块")
public class BindController {
	private final Map<String, BindService> bindServiceMap;

	@Inner(false)
	@PutMapping("{bindName}")
	@ApiOperation(value = "绑定操作",
			notes = "绑定名称{bindName}分为手机绑定{mobileBind}和第三方绑定{thirdBind} \n" +
					"绑定操作{bindType}详见BindTypeEnum枚举:0解绑|1绑定|2重新绑定|3有订单重新绑定(待定)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bindName", value = "绑定名称", required = true, dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "bindType", value = "绑定操作", required = true, dataType = "Integer", paramType = "body"),
			@ApiImplicitParam(name = "mobile", value = "绑定手机号", dataType = "Integer", paramType = "body"),
			@ApiImplicitParam(name = "code", value = "绑定手机验证码", dataType = "String", paramType = "body"),
			@ApiImplicitParam(name = "oldIden", value = "旧三方标识", dataType = "String", paramType = "body"),
			@ApiImplicitParam(name = "newIden", value = "新三方标识", dataType = "String", paramType = "body"),
			@ApiImplicitParam(name = "thirdType", value = "三方标识类型", dataType = "Integer", paramType = "body"),
	})
	public R bind(@PathVariable String bindName, @RequestBody BindDTO request) {
		BindService service = bindServiceMap.get(bindName);
		if (null == service) {
			return R.failed(ErrorCodeEnum.NOT_FOUND);
		}
		return service.binding(request);
	}
}
