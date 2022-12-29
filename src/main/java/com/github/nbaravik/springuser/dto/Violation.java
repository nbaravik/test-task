package com.github.nbaravik.springuser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Violation {

    String field;
    String message;
}
