//package org.lemon.common.security.service.impl;
//
//import cn.hutool.core.util.ArrayUtil;
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.lemon.common.security.service.LemonUserService;
//import org.lemon.common.security.util.GbUser;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * 运营管理后台用户信息获取
// *
// * @author Donald
// */
//@Slf4j
//@Component("OMS")
//@AllArgsConstructor
//public class LemonOmsUserServiceImpl implements LemonUserService {
//	private final RemoteOprService remoteOprService;
//	private final CacheManager cacheManager;
//
//	@Override
//	public UserDetails info(String username) throws UsernameNotFoundException {
//		Cache cache = cacheManager.getCache(CacheConstant.OPR_DETAILS);
//		if (cache != null && cache.get(username) != null) {
//			return (GbUser) cache.get(username).get();
//		}
//		R<OprInfo> result = remoteOprService.getByUsername(username, SecurityConstant.FROM_IN);
//		UserDetails userDetails = getOprDetails(result);
//		cache.put(username, userDetails);
//		return userDetails;
//	}
//
//	@Override
//	public UserDetails socialInfo(String openId) throws UsernameNotFoundException {
//		return null;
//	}
//
//	/**
//	 * 构建userdetails
//	 *
//	 * @param result
//	 * @return
//	 */
//	private UserDetails getOprDetails(R<OprInfo> result) {
//		if (result == null) {
//			throw new UsernameNotFoundException("用户不存在");
//		}
//		if (null == result.getData()) {
//			throw new BusinessException(result.getMsg());
//		}
//		OprInfo oprInfo = result.getData();
//		Set<String> dbAuthsSet = new HashSet<>();
//		Collection<? extends GrantedAuthority> authorities
//				= AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
//		if (ArrayUtil.isNotEmpty(oprInfo.getRoles())) {
//			Arrays.stream(oprInfo.getRoles()).forEach(roleCode -> dbAuthsSet.add(SecurityConstant.ROLE + roleCode));
//			dbAuthsSet.addAll(Arrays.asList(oprInfo.getAuths()));
//			authorities = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
//		}
//		Opr opr = oprInfo.getOpr();
//		boolean enabled = StrUtil.equals(opr.getOprStatus().toString(), CommonConstant.STATUS_NORMAL);
//		// 构造security用户
//		return new GbUser(opr.getOprId().longValue(), opr.getMobile(), opr.getTenantId(),
//				SystemTypeEnum.OMS.getType() + StringPool.AT + opr.getUsername(),
//				SecurityConstant.BCRYPT + opr.getPassword(), enabled,
//				true, true,
//				!CommonConstant.STATUS_DISABLE.equals(opr.getOprStatus()), authorities);
//	}
//}
