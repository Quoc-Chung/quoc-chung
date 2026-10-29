package com.quocchung.study_git_reverse.service.impl;

import com.quocchung.study_git_reverse.dto.UserRequest;
import com.quocchung.study_git_reverse.dto.UserResponse;
import com.quocchung.study_git_reverse.entity.AppUser;
import com.quocchung.study_git_reverse.repository.UserRepository;
import com.quocchung.study_git_reverse.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return toResponse(findUserById(id));
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim();
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên đăng nhập đã tồn tại");
        }
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email người dùng đã tồn tại");
        }

        AppUser user = new AppUser();
        copyRequestToEntity(request, user);
        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        AppUser user = findUserById(id);
        String username = request.getUsername().trim();
        String email = request.getEmail().trim();
        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(username, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên đăng nhập đã tồn tại");
        }
        if (userRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email người dùng đã tồn tại");
        }

        copyRequestToEntity(request, user);
        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(findUserById(id));
    }

    private AppUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy người dùng với id: " + id));
    }

    private void copyRequestToEntity(UserRequest request, AppUser user) {
        user.setUsername(request.getUsername().trim());
        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim());
        user.setPhone(request.getPhone() == null ? null : request.getPhone().trim());
        user.setAddress(request.getAddress() == null ? null : request.getAddress().trim());
    }

    private UserResponse toResponse(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress());
    }
}
