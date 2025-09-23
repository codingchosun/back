package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @Setter
    private String content;

    @Setter
    private int score;

    @OneToMany(mappedBy = "template")
    private List<Evaluation> evaluations;

    public Template(String content, int score) {
        this.content = content;
        this.score = score;
    }
}
