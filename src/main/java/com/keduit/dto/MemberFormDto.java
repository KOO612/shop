package com.keduit.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {
    
    @NotBlank(message = "이름 필수 입력 값")
    private String name;
    
    @NotEmpty(message = "이메일은 필수 입력 값")
    @Email(message = "이메일 형식으로 입력")
    private String email;


    @NotEmpty(message = "비밀번호는 필수 입력 값")
    @Length(min = 4, max = 16, message = "비밀번호 4 ~ 16 사이")
    private String password;

    @NotEmpty(message = "주소는 필수 입력 값")
    private String address;
}
