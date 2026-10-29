package com.quocchung.study_git_reverse.dto;

import java.math.BigDecimal;

public record EmployeeResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        String position,
        BigDecimal salary
) {
}
