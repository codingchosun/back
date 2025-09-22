package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private Long imageId;
    private String uploadFileName;
    private String url;

    public static ImageResponse from(Image image) {
        return new ImageResponse(
                image.getImageId(),
                image.getName(),
                image.getUrl()
        );
    }

    public ImageResponse(Image image) {
        this.imageId = image.getImageId();
        this.uploadFileName = image.getName();
        this.url = image.getUrl();
    }
}
