package com.codingchosun.backend.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ImageDeleteRequest {
    private Long deleteImageId;
}
