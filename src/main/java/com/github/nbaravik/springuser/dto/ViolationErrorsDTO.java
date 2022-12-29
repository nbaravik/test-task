package com.github.nbaravik.springuser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ViolationErrorsDTO {

        private List<Violation> violations;
}
