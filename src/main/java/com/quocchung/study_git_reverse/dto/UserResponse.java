package com.quocchung.study_git_reverse.dto;

public record UserResponse(
        Long id,
        String username,
        String fullName,
        String email,
        String phone,
        String address
) {
}
