package org.lemon.user.api.feign;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lemon.common.core.constant.SecurityConstant;
import org.lemon.common.core.constant.ServiceNameConstant;
import org.lemon.common.core.util.R;
import org.lemon.user.api.dto.request.LoginDTO;
import org.lemon.user.api.dto.request.RestPwdDTO;
import org.lemon.user.api.dto.request.ScrollByUserNoDTO;
import org.lemon.user.api.dto.request.UserPageDTO;
import org.lemon.user.api.dto.response.UserInfoDTO;
import org.lemon.user.api.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户服务接口
 *
 * @author Ervin
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstant.USER_SERVICE)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param from     调用标志
     * @return R
     */
    @GetMapping("/info/{username}")
    R<UserInfo> getByUsername(@PathVariable("username") String username
            , @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 通过手机号查询用户信息
     *
     * @param from   调用标志
     * @param mobile
     * @return R
     */
    @GetMapping("/mobile/info/{mobile}")
    R<UserInfo> getByMobile(@PathVariable("mobile") String mobile, @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 根据条件查询用户分页
     */
    @PostMapping("/info/page")
    R<Page<UserInfo>> page(@RequestParam("current") Long current, @RequestParam("size") Long size,
                           @RequestBody UserPageDTO params, @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 通过社交账号查询用户信息
     *
     * @param openId openId
     * @param from   调用标志
     * @return
     */
    @GetMapping("/social/info/{openId}")
    R<UserInfo> getBySocial(@PathVariable("openId") String openId, @RequestHeader(SecurityConstant.FROM) String from);

    @PutMapping("/password/reset")
    R<Long> resetPassword(@RequestBody RestPwdDTO request, @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 通过userNo查找对应用户信息
     *
     * @param userNo userNo
     * @param from   调用标志
     * @return R<userInfo>
     */
    @GetMapping("/info/getByUserNo")
    R<UserInfo> getByUserNo(@RequestParam("userNo") Long userNo, @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 获取用户列表
     */
    @PostMapping("/oms/scrollByUserNo")
    R<List<UserInfoDTO>> scrollByUserNo(@RequestBody ScrollByUserNoDTO request, @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 批量修改用户昵称
     */
    @PutMapping("/oms/changeNickName")
    R changeNickName(@RequestBody Map<Long, String> changeMap, @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 用户登陆后续处理
     * 日志 && 踢人 && 最后登陆时间
     */
    @PostMapping("/login")
    void login(@RequestBody LoginDTO dto, @RequestHeader(SecurityConstant.FROM) String from);

    @PutMapping("/update")
    R update(@RequestBody UserInfo params, @RequestHeader(SecurityConstant.FROM) String from);

    /**
     * 获取拷贝升级所需用户,根据要升级的租户ID分组
     */
    @PostMapping("/extend/getCopyUsers")
    R<Map<Integer, List<UserInfo>>> getCopyUsers(@RequestBody List<Integer> tenantIds,
                                                 @RequestParam("size") Integer size,
                                                 @RequestParam("limit") Integer limit,
                                                 @RequestHeader(SecurityConstant.FROM) String from);
}
