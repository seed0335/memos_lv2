package com.sparta.memos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 전략
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;



    //JPA 사용시 기본 생성자 필요
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}