package com.beyond.university.auth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Serial
    private static final long serialVersionUID = -4304936958936029047L;

    private int no;

    private String username;

    private String password;

    private String role;

    private String nickname;

    private String phone;

    private String email;

    private String address;

    private String hobby;

    private String status;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    // getUsername(), getPassword()는 Lombok Getter에서 같이 만들어줌

    // 사용자를 식별할 수 있는 사용자의 이름(ID)를 반환
    @Override
    public String getUsername() {
        return this.username;
    }

    // 사용자의 비밀번호를 반환
    @Override
    public String getPassword() {
        return this.password;
    }

    // 권한 - role을 그대로 가져옴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }

    // is~는 계정의 상태를 확인

    // 계정이 만료되지 않았는지 확인 - 만료되지 않았으면 true(기본값)
    // 관리 방법이 없으므로 전부 true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 확인 - 잠기지 않았으면 true(기본값)
    // 관리 방법이 없으므로 전부 true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 확인
    // 관리할 데이터가 없으므로 전부 true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 - 활성화된 계정이면 true
    // status = Y이면 활성화
    @Override
    public boolean isEnabled() {
        return this.status.equals("Y");
    }
}
