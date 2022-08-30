package pl.damian.demor.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Sets;

public enum UserRole {

    BASIC_USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(UserPermission.GET_ALL_USERS));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities =
                getPermissions()
                        .stream()
                        .map(perm -> new SimpleGrantedAuthority(perm.getPermission()))
                        .collect((Collectors.toSet()));
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    public String getUserRoleName(){
        return "ROLE_" + this.name();
    }
}
