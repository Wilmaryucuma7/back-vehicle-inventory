package com.technicaltest.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
