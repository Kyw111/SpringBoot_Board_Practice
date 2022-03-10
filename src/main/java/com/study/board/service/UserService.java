package com.study.board.service;

import com.study.board.entity.Role;
import com.study.board.entity.User;
import com.study.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword()); // user가 입력한 비밀번호를 암호화하여 변수에 대입 - passwordEncoder.encode
        user.setPassword(encodedPassword); //위에서 암호화한 password를 setPassword로 설정해주기
        user.setEnabled(true); //회원가입을 하게되면 활성화되도록 함
        Role role = new Role();
        role.setId(1);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
