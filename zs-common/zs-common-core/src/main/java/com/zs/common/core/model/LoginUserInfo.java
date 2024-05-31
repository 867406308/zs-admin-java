package com.zs.common.core.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
public class LoginUserInfo implements UserDetails {

    @Setter
    @Getter
    public SysUser sysUser;

    @Setter
    @Getter
    private Set<String> permissions;

    @JSONField(serialize = false)
    private Collection<GrantedAuthority> authorities;


    public LoginUserInfo(SysUser sysUser, Set<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return authorities;

    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    /**
     * 用户没过期返回true，反之则false
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 用户没锁定返回true，反之则false
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 用户凭据(通常为密码)没过期返回true，反之则false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 用户凭据(通常为密码)没过期返回true，反之则false
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

}
