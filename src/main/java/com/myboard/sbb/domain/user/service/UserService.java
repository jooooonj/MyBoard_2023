package com.myboard.sbb.domain.user.service;

import com.myboard.sbb.domain.user.entity.SiteUser;
import com.myboard.sbb.domain.user.form.SignupForm;
import com.myboard.sbb.domain.user.repository.UserRepository;
import com.myboard.sbb.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser getUser(String userId){
        Optional<SiteUser> findUser =
                userRepository.findByUserId(userId);
        if (findUser.isPresent()) {
            return findUser.get();
        }
        throw new DataNotFoundException(userId + "은 존재하지 않습니다.");
    }

    public SiteUser getUser(long id){
        Optional<SiteUser> findUser =
                userRepository.findById(id);
        if (findUser.isPresent()) {
            return findUser.get();
        }
        throw new DataNotFoundException("존재하지 않는 회원입니다.");
    }

    public SiteUser register(String userId, String password,String username, String email) {
        SiteUser user = SiteUser.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .email(email)
                .build();
        return userRepository.save(user);
    }

    public SiteUser register(String userId, String password,String username) {
        SiteUser user = SiteUser.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .username(username)
                .build();

        return userRepository.save(user);
    }


    public SiteUser register(SignupForm signupForm) {
        SiteUser user = SiteUser.builder()
                .userId(signupForm.getUserId())
                .password(passwordEncoder.encode(signupForm.getPassword()))
                .username(signupForm.getUsername())
                .email(signupForm.getEmail())
                .build();

        return userRepository.save(user);
    }
}
