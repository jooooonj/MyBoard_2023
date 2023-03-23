package com.myboard.sbb.domain.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {

    @NotEmpty(message = "아이디는 필수값입니다.")
    @Size(min = 3, max = 25)
    private String userId;

    @NotEmpty(message = "비밀번호는 필수값입니다.")
    private String password;
    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    private String password2;

    @NotEmpty(message = "이름은 필수값입니다.")
    private String username;

    @Email(message = "이메일 형식으로 입력해주세요.")
    @NotEmpty(message = "이메일은 필수값입니다.")
    private String email;
}
