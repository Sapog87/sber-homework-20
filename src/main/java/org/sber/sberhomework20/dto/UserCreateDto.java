package org.sber.sberhomework20.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String login;
}