package org.lemon.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lemon.common.entity.UserInfo;
import org.lemon.common.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author ErvinWang
 */
@Service
public class LemonUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserInfo> query = Wrappers.lambdaQuery();
        query.eq(UserInfo::getUsername, username);
        UserInfo userInfo = userInfoMapper.selectOne(query);
        if (Objects.isNull(userInfo)) {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
        return new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getUserStatus() == 1, true, true, true, getAuthorities());
    }

    public List<GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getRoles());
    }


    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
