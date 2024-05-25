package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;
    private final DataJpaPostRepository dataJpaPostRepository;

    @PostMapping(value = "/posts/{postId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse<Integer> saveImages(@RequestParam("files") List<MultipartFile> multipartFiles, @PathVariable Long postId){
        
        log.info("멀티파트 폼 데이터 크기 = {}",multipartFiles.size());

        Post post = dataJpaPostRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("postId: " + postId + "를 찾지 못했습니다"));

        int count = imageService.uploadImages(multipartFiles, post);    //저장된 파일 개수

        return new ApiResponse<>(HttpStatus.OK, true, count);
    }
}
