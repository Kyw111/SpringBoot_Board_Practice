package com.study.board.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role", // DB에 만든 테이블이름
            joinColumns = @JoinColumn(name = "user_id"), // 여기가 User클래스니까 user_id를 먼저
            inverseJoinColumns = @JoinColumn(name = "role_id")) // 반대편 조인되는 쪽
    private List<Role> roles = new ArrayList<>(); //여기가 User클래스니까 Role클래스를 속성으로 지정

}
