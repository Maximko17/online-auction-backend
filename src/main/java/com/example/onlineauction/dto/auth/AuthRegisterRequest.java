package com.example.onlineauction.dto.auth;

import com.example.onlineauction.dto.validation.OnCreate;
import com.example.onlineauction.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthRegisterRequest {

    @NotNull(message = "Name must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Email must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Email length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.", groups = {OnCreate.class})
    private String passwordConfirmation;

}
