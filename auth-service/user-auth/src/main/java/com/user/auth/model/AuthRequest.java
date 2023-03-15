package com.user.auth.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class AuthRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+",message = "Invalid username")
    private String username;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+",message = "Invalid password")
    private String password;
}
