package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String url;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

}
