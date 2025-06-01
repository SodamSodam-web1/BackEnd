package goormton.backend.sodamsodam.domain.user.application;

import goormton.backend.sodamsodam.domain.user.domain.User;
import goormton.backend.sodamsodam.domain.user.dto.request.LoginRequest;
import goormton.backend.sodamsodam.domain.user.dto.request.SignupRequest;
import goormton.backend.sodamsodam.domain.user.dto.response.JwtResponse;

public interface UserService {

    public void addUser(SignupRequest request);

    public JwtResponse signupAndLogin(SignupRequest request);

    public JwtResponse loginAndGetToken(LoginRequest request);

    public User getUser(Long id);

    public boolean verifyCurrentPassword(User user, String rawPassword);
}
