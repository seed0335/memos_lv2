package com.sparta.memos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class SignupRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
