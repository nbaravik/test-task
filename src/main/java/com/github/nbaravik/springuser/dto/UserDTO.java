package com.github.nbaravik.springuser.dto;

import com.github.nbaravik.springuser.model.StatusMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import com.github.nbaravik.springuser.validation.ValueOfEnum;

@Data
@Builder
public class UserDTO {

    @NotBlank(message = "must not be blank")
    private String name;

    @Email(message = "must be a well-formed")
    @NotBlank(message = "must not be blank")
    private String email;

    @URL(message = "must be a well-formed")
    private String uri;

    @NotBlank(message = "must not be blank")
    @ValueOfEnum(enumClass = StatusMode.class, message = "must be any of enum {ONLINE, OFFLINE}")
    private String status;
}
