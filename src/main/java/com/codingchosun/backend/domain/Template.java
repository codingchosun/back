package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table
public class Template {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    private String content;

    private int score;

    @OneToMany(mappedBy = "template")
    private List<Validate> validates;

}
