package com.codingchosun.backend.controller;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.exception.invalidrequest.IsNotPostAuthor;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import com.codingchosun.backend.request.ImageDeleteRequest;
import com.codingchosun.backend.response.ApiResponse;
import com.codingchosun.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;
    private final DataJpaPostRepository dataJpaPostRepository;
    private final DataJpaUserRepository dataJpaUserRepository;

    @PostMapping(value = "/posts/{postId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse<Integer> saveImages(@RequestParam(value = "files") List<MultipartFile> files,
                                           @PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetails userDetails){
        
        log.info("멀티파트 폼 데이터 크기 = {}", files.size());

        if(userDetails==null){ //로그인 검사
            throw new LoggedInUserNotFound("로그인 하세요");
        }
        User user = this.getUserFromUserDetails(userDetails);

        Post post = dataJpaPostRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("postId: " + postId + "를 찾지 못했습니다"));

        //log.info("로그인 유저: {}, 글 작성자:{}",user.getUserId(), post.getUser().getUserId());

        //글 작성자와 로그인된 유저가 같은지 검사
        if( !(post.getUser().getUserId().equals(user.getUserId())) ){
            throw new IsNotPostAuthor("로그인 유저:" + user.getUserId() + "가 글 작성자:" + post.getUser().getUserId() + "와 다릅니다");
        }

        int count = imageService.uploadImages(files, post);    //저장된 파일 개수

        return new ApiResponse<>(HttpStatus.OK, true, count);
    }

    //이미지 삭제
    @PostMapping(value = "/posts/{posdId}/deleteimage")
    @ResponseBody
    public ApiResponse<Long> dropImage(@PathVariable Long posdId,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody ImageDeleteRequest imageDeleteRequest){

        if(userDetails==null){ //로그인 검사
            throw new LoggedInUserNotFound("로그인 하세요");
        }
        User user = this.getUserFromUserDetails(userDetails);

        //포스트 찾기
        Post post = dataJpaPostRepository.findById(posdId)
                .orElseThrow(() -> new PostNotFoundFromDB("postId = " + posdId + "를 찾지 못했습니다"));

        Long deletedImageId = imageService.deleteImage(user, post, imageDeleteRequest.getDeleteImageId());
        return new ApiResponse<>(HttpStatus.OK, true, deletedImageId);
    }

    public User getUserFromUserDetails(UserDetails userDetails){
        return dataJpaUserRepository.findByLoginId(userDetails.getUsername());
    }
}
