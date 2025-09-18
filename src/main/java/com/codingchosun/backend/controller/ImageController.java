package com.codingchosun.backend.controller;

import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.dto.response.ImageResponse;
import com.codingchosun.backend.service.ImageQueryService;
import com.codingchosun.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    //이미지 조회
    @GetMapping
    public ResponseEntity<Page<ImageResponse>> getImages(@PathVariable Long postId, Pageable pageable) {
        Page<ImageResponse> imageResponses = imageQueryService.getImages(postId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(imageResponses);
    }

    //이미지 저장
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImages(@PathVariable Long postId,
                                               @RequestParam(value = "files") List<MultipartFile> files,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new LoggedInUserNotFound("로그인 유저만 게시물에 이미지를 첨부할 수 있습니다");
        }
        imageService.uploadImages(postId, userDetails.getUsername(), files);

        return ResponseEntity.status(HttpStatus.CREATED).body("이미지 저장 성공");
    }

    //이미지 삭제
    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long postId,
                                              @PathVariable Long imageId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new LoggedInUserNotFound("로그인 유저만 게시물에 이미지를 삭제할 수 있습니다");
        }

        imageService.deleteImage(postId, imageId, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body("이미지 삭제 성공");
    }

}
