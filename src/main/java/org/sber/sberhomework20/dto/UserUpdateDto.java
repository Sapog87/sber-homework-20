package org.sber.sberhomework20.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String login;
}
