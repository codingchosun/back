package com.codingchosun.backend.response;

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

    private static String urlParsing(String url) {
        if (url != null) {
            String[] urlSplit = url.split("/");
            url = urlSplit[urlSplit.length - 1];
        }
        return url;
    }

    public static ImageResponse from(Image image) {
        return new ImageResponse(
                image.getImageId(),
                image.getName(),
                urlParsing(image.getUrl())
        );
    }

    public ImageResponse(Image image) {
        this.imageId = image.getImageId();
        this.uploadFileName = image.getName();
        this.url = image.getUrl();
    }
}
