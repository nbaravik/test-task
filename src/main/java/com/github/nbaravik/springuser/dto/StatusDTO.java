package com.github.nbaravik.springuser.dto;

import com.github.nbaravik.springuser.model.StatusMode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.github.nbaravik.springuser.validation.ValueOfEnum;

@Data
@Builder
public class StatusDTO {

    @NotBlank(message = "must not be blank")
    private String id;

    @NotBlank(message = "must not be blank")
    @ValueOfEnum(enumClass = StatusMode.class, message = "must be any of enum {ONLINE, OFFLINE}")
    private String status;

    @NotBlank(message = "must not be blank")
    @ValueOfEnum(enumClass = StatusMode.class, message = "must be any of enum {ONLINE, OFFLINE}")
    private String previousStatus;
}
