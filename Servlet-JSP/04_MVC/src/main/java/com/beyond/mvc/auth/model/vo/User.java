package com.beyond.mvc.auth.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@Builder
@ToString
public class User {
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

    private Date createdAt;

    private Date updatedAt;
}
