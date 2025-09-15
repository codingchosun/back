package com.codingchosun.backend.domain;

import com.codingchosun.backend.constants.GenderCode;
import com.codingchosun.backend.constants.StateCode;
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
@Getter
@Setter
@Table
@Generated
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    @NotNull
    @Size(min = 2, max = 16)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Past
    private LocalDate birth;

    private String introduction;

    @NotNull
    @Size(min = 2, max = 12)
    @Column(unique = true)
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StateCode stateCode;

    private int score;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderCode genderCode;

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

    public User(RegisterUserRequest register) {
        this.loginId = register.getLoginId();
        this.password = register.getPassword();
        this.email = register.getEmail();
        this.birth = register.getBirth();
        this.nickname = register.getNickname();
        this.name = register.getName();
        this.genderCode = register.getGenderCode();
        this.stateCode = StateCode.ACTIVE;
    }

    @Override
    public String toString() {
        return "\n유저아이디 : " + this.userId + "\n로그인아이디 : " + this.loginId + "\n 닉네임 : " + this.nickname + "\n 성별 + " + this.genderCode +
                "\n 자기소개 : " + this.introduction + "\n 이메일 : " + this.email + "\n 생일 : " + this.birth +
                "\n 닉네임 : " + this.nickname + "\n 매너점수 : " + this.score + "\n 상태 : " + this.stateCode;
    }

    public void setUpdateRequest(UserUpdateRequest updateRequest) {
        this.nickname = updateRequest.getNickname();
        this.password = updateRequest.getPassword();
        this.email = updateRequest.getEmail();
        this.genderCode = updateRequest.getGenderCode();
        this.introduction = updateRequest.getIntroduction();
    }

    public int calMannerScore(int mannersScore) {
        //로직: 기존의 매너 점수를 더하고, 점수 총합이 100점을 초과할 경우 100으로 고정시키거나 0점 아래인 경우 0점으로 고정시킴 -> 점수 검증을 할때, 이 메서드의 반환값을 사용하지 않는 문제 발생
        return this.score = Math.max(0, Math.min(100, this.score + mannersScore));
    }
}