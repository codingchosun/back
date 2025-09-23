package com.codingchosun.backend.controller.image;

import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.ImageResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.service.image.ImageQueryService;
import com.codingchosun.backend.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageQueryService imageQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ImageResponse>>> getImages(@PathVariable Long postId, Pageable pageable) {
        Page<ImageResponse> imageResponses = imageQueryService.getImages(postId, pageable);

        return ApiResponse.ok(imageResponses);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadImages(@PathVariable Long postId,
                                                            @RequestParam(value = "files") List<MultipartFile> files,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        imageService.uploadImages(postId, userDetails.getUsername(), files);

        return ApiResponse.created("이미지 저장 성공");
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse<String>> deleteImage(@PathVariable Long postId,
                                                           @PathVariable Long imageId,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new NotAuthenticatedException(ErrorCode.AUTHENTICATION_REQUIRED);
        }

        imageService.deleteImage(postId, imageId, userDetails.getUsername());
        return ApiResponse.ok("이미지 삭제 성공");
    }

}
