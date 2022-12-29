package com.github.nbaravik.springuser.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UserIdDTO {

    @NotBlank(message = "must not be blank")
    private String id;
}
