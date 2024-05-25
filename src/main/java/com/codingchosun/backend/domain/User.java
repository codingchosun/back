package com.codingchosun.backend.domain;

import com.codingchosun.backend.constants.GenderCode;
import com.codingchosun.backend.request.RegisterUserRequest;
import com.codingchosun.backend.request.UserUpdateRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDate;
import java.util.List;


@Entity
@Getter @Setter
@Table
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull @Size(min = 6, max = 16)
    private String loginId;

    @NotNull @Size(min = 6, max = 16)
    private String password;

    @NotNull @Size(min = 2, max = 16)
    private String name;

    @NotNull @Email
    private String email;

    @NotNull @Past
    private LocalDate birth;

    private String introduction;

    @NotNull @Size(min = 2, max = 12)
    private String nickname;

    private int state;

    private int score;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "GENDER_ID")
//    private Gender genderName;
//
//    @OneToMany(mappedBy = "User")
//    private List<Post> posts = new ArrayList<>();
//
    @OneToMany(mappedBy = "user")
    private List<PostUser> postUsers;

    @OneToMany(mappedBy = "user")
    private List<UserHash> userHashes;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "fromUser")
    private List<Validate> fromValidates;
    @OneToMany(mappedBy = "toUser")
    private List<Validate> toValidates;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @NotNull @Enumerated(EnumType.STRING)
    private GenderCode genderCode;

    //
    //    public User() {}

    public User(RegisterUserRequest register){
        this.loginId = register.getLoginId();
        this.password = register.getPassword();
        this.email = register.getEmail();
        this.birth =  register.getBirth();
        this.nickname = register.getNickname();
        this.name = register.getName();
        this.genderCode = register.getGenderCode();
    }

    @Override
    public String toString() {
        return "로그인아이디 : " + this.loginId + "\n 닉네임 : " + this.nickname + "\n 성별 + " + this.genderCode +
                "\n 자기소개 : " + this.introduction + "\n 이메일 : " + this.email + "\n 생일 : " + this.birth +
                "\n 닉네임 : " + this.nickname  + "\n 매너점수 : " + this.score + "\n 상태 : " + this.state;
    }

    public void setUpdateRequest(UserUpdateRequest updateRequest) {
        this.nickname = updateRequest.getNickname();
        this.password = updateRequest.getPassword();
        this.email = updateRequest.getEmail();
        this.genderCode = updateRequest.getGenderCode();
        this.introduction = updateRequest.getIntroduction();
    }

    public int calMannerScore(int score) {
        this.score += score;
        if (this.score > 100) {
            score = 100;
        } else if (this.score < 0) {
            score = 0;
        }
        return this.score;
    }
}