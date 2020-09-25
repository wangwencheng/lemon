//package org.lemon.auth.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import lombok.AllArgsConstructor;
//import org.lemon.auth.dto.request.RestPwd;
//import org.lemon.auth.service.SystemCommonService;
//import org.lemon.common.core.util.R;
//import org.lemon.common.security.annotation.Inner;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/sso")
//@Api(value = "sso", tags = "sso模块")
//public class UserController {
//	private final Map<String, SystemCommonService> systemCommonServiceMap;
//
//	@Inner(false)
//	@PutMapping("/password/reset/{regType}")
//	@ApiOperation(value = "重置密码")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String", paramType = "body"),
//			@ApiImplicitParam(name = "code", value = "短信验证码", required = true, dataType = "String", paramType = "body"),
//	})
//	public R resetPassword(@PathVariable("regType") String regType, @RequestBody RestPwd params) {
//		SystemCommonService service = systemCommonServiceMap.get(regType);
//		if (null == service) {
//			return R.failed();
//		}
//		return service.resetPassword(params);
//	}
//}
