package com.beyond.mvc.auth.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

// @JsonIgnore를 붙이면 json으로 객체를 만들 때 무시된다.
// public record UserResponseDto(int no, String userName, String address) {
public record UserResponseDto(int no, String userName, @JsonIgnore String address) {
}
