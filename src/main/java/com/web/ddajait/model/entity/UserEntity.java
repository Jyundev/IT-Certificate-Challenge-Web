package com.web.ddajait.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserEntity")
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isLogin;

    @Column(name = "interest", length = 1000)
    private String interest;

    @Column(name = "job", length = 100)
    private String job;

    @Column(name = "profileImage", length = 255)
    private String profileImage;

    @Column(name = "tier")
    private int tier;

    @Column(name = "qualifiedCertificate", length = 1000)
    private String qualifiedCertificate;
    
    // 일반사용자 / 관리자를 구분용
    @Column(name = "role", length = 50)
    private String role; 

}
