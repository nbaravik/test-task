package com.github.nbaravik.springuser.service;

import com.github.nbaravik.springuser.dto.StatusDTO;
import com.github.nbaravik.springuser.dto.UserDTO;
import com.github.nbaravik.springuser.dto.UserIdDTO;
import com.github.nbaravik.springuser.model.StatusMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import com.github.nbaravik.springuser.validation.ValueOfEnum;

@Validated
public interface UserService {

    UserIdDTO createUser(@Valid UserDTO user);

    UserDTO getUserById(@NotBlank(message = "user name must not be blank") String id);

    StatusDTO modifyStatus(@NotBlank(message = "user name must not be blank") String id,
                           @NotBlank(message = "must not be blank")
                           @ValueOfEnum(enumClass = StatusMode.class,
                                   message = "must be any of enum {ONLINE, OFFLINE}") String newStatus);
}
