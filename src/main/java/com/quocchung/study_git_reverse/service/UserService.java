package com.quocchung.study_git_reverse.service;

import com.quocchung.study_git_reverse.dto.UserRequest;
import com.quocchung.study_git_reverse.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
