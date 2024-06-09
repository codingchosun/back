package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Image;
import lombok.Data;

@Data
public class ImageResponse {
    private Long imageId;
    private String url;

    public ImageResponse(Image image){
        this.imageId = image.getImageId();

        if(image.getUrl() != null){
            String[] split = image.getUrl().split("/");
            this.url = split[split.length - 1];
        }
        else{
            this.url = "";
        }
    }
}
