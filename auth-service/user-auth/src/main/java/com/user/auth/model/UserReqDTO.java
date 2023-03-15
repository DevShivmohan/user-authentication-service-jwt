package com.user.auth.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class UserReqDTO {

    @NotBlank
    @NotNull
    @Size(min = 3,max = 30)
    private String name;
    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+",message = "Username must be contain upper and lower case")
    @Size(min = 8,max = 50)
    private String username;
    @NotNull
    @NotBlank
    @Size(min = 8,max = 50)
    @Pattern(regexp = "[a-zA-Z0-9]+",message = "Password must contain alpha numeric with upper and lower case")
    private String password;
//    private Set<RoleReqDTO> roles;
}
