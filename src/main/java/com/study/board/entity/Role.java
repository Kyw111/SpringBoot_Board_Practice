package com.study.board.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "roles") //양방향맵핑 - User클래스의 roles속성의 이름과 일치해야함
    private List<User> users; //여기가 Role클래스니까 User클래스를 속성으로 지정

}
