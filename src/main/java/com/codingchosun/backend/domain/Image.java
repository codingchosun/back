package com.codingchosun.backend.domain;

import com.codingchosun.backend.component.file.UploadFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Setter
    private String url;

    @Setter
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    public Image(String name, Post post, UploadFile uploadFile) {
        this.post = post;
        this.url = "/images/" + uploadFile.getStoreFileName();
        this.name = name;
    }
}
