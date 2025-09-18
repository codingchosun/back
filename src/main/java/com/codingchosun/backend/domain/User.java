package com.codingchosun.backend.domain;

import com.codingchosun.backend.constants.GenderCode;
import com.codingchosun.backend.constants.Role;
import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.exception.PasswordNotMatch;
import com.codingchosun.backend.request.RegisterUserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Size(min = 6, max = 16)
    @Column(unique = true)
    private String loginId;

    @NotNull
    private String password;

    @Setter
    @NotNull
    @Size(min = 2, max = 16)
    private String name;

    @Setter
    @NotNull
    @Email
    private String email;

    @NotNull
    @Past
    private LocalDate birth;

    @Setter
    private String introduction;

    @Setter
    @NotNull
    @Size(min = 2, max = 12)
    @Column(unique = true)
    private String nickname;

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private StateCode stateCode;

    @Setter
    private int score;

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderCode genderCode;

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<PostUser> postUsers;

    @OneToMany(mappedBy = "user")
    private List<UserHash> userHashes;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "fromUser")
    private List<Evaluation> fromEvaluations;

    @OneToMany(mappedBy = "toUser")
    private List<Evaluation> toEvaluations;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(RegisterUserRequest register) {
        this.loginId = register.getLoginId();
        this.email = register.getEmail();
        this.birth = register.getBirth();
        this.nickname = register.getNickname();
        this.name = register.getName();
        this.score = 50;
        this.genderCode = register.getGenderCode();
        this.stateCode = StateCode.ACTIVE;
        this.role = Role.USER;
    }

    @Override
    public String toString() {
        return "\n유저아이디 : " + this.userId + "\n로그인아이디 : " + this.loginId + "\n 닉네임 : " + this.nickname + "\n 성별 + " + this.genderCode +
                "\n 자기소개 : " + this.introduction + "\n 이메일 : " + this.email + "\n 생일 : " + this.birth +
                "\n 닉네임 : " + this.nickname + "\n 매너점수 : " + this.score + "\n 상태 : " + this.stateCode;
    }

    public void passwordEncode(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updatePassword(String currentPassword, String newPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(currentPassword, this.password)) {
            throw new PasswordNotMatch("기존 비밀번호가 일치하지 않습니다");
        }
        this.password = passwordEncoder.encode(newPassword);
    }

    public int calMannerScore(int mannersScore) {
        return this.score = Math.max(0, Math.min(100, this.score + mannersScore));
    }
}