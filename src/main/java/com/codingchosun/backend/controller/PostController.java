package com.codingchosun.backend.controller;


import com.codingchosun.backend.constants.DeleteConstants;
import com.codingchosun.backend.constants.PagingConstants;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.LoggedInUserNotFound;
import com.codingchosun.backend.exception.invalidrequest.YouAreNotAdmin;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.PostUpdateRequest;
import com.codingchosun.backend.request.RegisterPostRequest;
import com.codingchosun.backend.request.RemoveUserFromPostRequest;
import com.codingchosun.backend.repository.postrepository.DataJpaPostRepository;
import com.codingchosun.backend.request.PostUpdateRequest;
import com.codingchosun.backend.request.RegisterPostRequest;
import com.codingchosun.backend.request.ResearchRequest;
import com.codingchosun.backend.response.*;
import com.codingchosun.backend.service.CommentService;
import com.codingchosun.backend.service.ImageService;
import com.codingchosun.backend.service.PostService;
import com.codingchosun.backend.service.PostUserService;
import com.codingchosun.backend.web.argumentresolver.Login;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final PostUserService postUserService;
    private final DataJpaUserRepository dataJpaUserRepository;

    private final DataJpaPostRepository dataJpaPostRepository;

    //작성한 모임글의 내용만 가져오는 컨트롤러 todo 예외 처리
    @GetMapping("/{postId}")
    public PostAndComments getPost(@PathVariable Long postId) {
        PostAndComments postAndComments = new PostAndComments();

        //게시글 넣기
        postAndComments.setPostResponse(postService.getPostResponse(postId));

        //댓글넣기
        Pageable commentsPageable = PageRequest.of(PagingConstants.DEFAULT_COMMENT_PAGE_NO, PagingConstants.MAX_COMMENT_SIZE,
                Sort.by(Sort.Direction.DESC, PagingConstants.DEFAULT_COMMENT_CRITERIA));
        postAndComments.setPagedCommentResponseList(commentService.getPagedComments(commentsPageable, postId));

        //이미지 url 넣기
        Pageable imageURLPageable = PageRequest.of(PagingConstants.DEFAULT_IMAGE_URL_PAGE_NO, PagingConstants.MAX_IMAGE_URL_SIZE,
                Sort.by(Sort.Direction.ASC, PagingConstants.DEFAULT_IMAGE_URL_CRITERIA));
        postAndComments.setPagedImageResponseList(imageService.getImageURLList(imageURLPageable, postId));

        return postAndComments;
    }

    //게시글 작성
    @PostMapping("/register")
    public ApiResponse<Long> registerPost(@RequestBody RegisterPostRequest registerPostRequest, BindingResult bindingResult,
                                    @Login User user){
        if(user == null){
            throw new LoggedInUserNotFound("로그인을 해야 글을 쓸수있습니다.");
        }
        Post registeredPost = postService.registerPost(registerPostRequest, user);
        return new ApiResponse<>(HttpStatus.OK, true, registeredPost.getPostId());
    }

    // 로그인 안 했을 때 글 보기
    @GetMapping
    public HttpEntity<NoLoginPostsHashtagsResponse> noLoginShowPosts(Pageable pageable)
    {
        return new ResponseEntity<>(postService.noLoginGetPosts(pageable), HttpStatus.OK);
    }
    // 로그인 했을 때 글 보기
    @GetMapping("/login")
    public HttpEntity<LoginPostsHashtagResponse> loginShowPosts(@Login User user,
                                                              Pageable pageable)
    {

        return new ResponseEntity<>(postService.loginPostsRequests(user, pageable), HttpStatus.OK);
    }

    //post 수정
    @PostMapping("/{postId}/edit")
    public  ApiResponse<Long> editPost(@PathVariable Long postId,
                                       @RequestBody PostUpdateRequest postUpdateRequest,
                                       @Login User user){
        if(user == null){
            throw new LoggedInUserNotFound("수정 중 로그인불량");
        }
        Post editedPost = postService.editPost(postId, user, postUpdateRequest);
        return new ApiResponse<>(HttpStatus.OK, true, editedPost.getPostId());
    }

    //post의 참가자 확인
    @GetMapping("/{postId}/participant")
    public List<UserDTO> getAllParticipants(@PathVariable Long postId){
        return postUserService.getParticipants(postId);
    }

    //post의 모임참가
    @PostMapping("/{postId}/participant")
    public ApiResponse<Long> particaptePost(@PathVariable Long postId, @Login User user){

        //로그인 검사
        if(user == null){
            throw new LoggedInUserNotFound("로그인해주세요");
        }

        User participant = postUserService.participate(postId, user);
        return new ApiResponse<>(HttpStatus.OK, true, participant.getUserId());
    }

    //post 모임 탈퇴(참가자 스스로 탈퇴)
    @PostMapping("/{postId}/leave")
    public ApiResponse<Long> leavePost(@PathVariable Long postId, @Login User user){

        //로그인 검사
        if(user == null){
            throw new LoggedInUserNotFound("로그인해주세요");
        }

        Post post = postService.getPost(postId).orElseThrow(() -> new PostNotFoundFromDB("post 못찾음"));

        Post leavePost = postUserService.leavePost(post, user);

        return new ApiResponse<>(HttpStatus.OK, true, leavePost.getPostId());   //혹시 모임페이지로 돌아갈수있으니 postId리턴
    }

    //post 모임 추방(방장이 추방)
    @PostMapping("/{postId}/admin/remove")
    public ApiResponse<Long> removePost(@RequestBody RemoveUserFromPostRequest removeUserFromPostRequest,
                                        @PathVariable Long postId,
                                        @Login User user){
        //로그인 검사
        if(user == null){
            throw new LoggedInUserNotFound("로그인해주세요");
        }

        Post post = postService.getPost(postId).orElseThrow(() -> new PostNotFoundFromDB("post 못찾음"));

        //글 작성자가 맞는지 검사
        if( !(post.getUser().getUserId().equals(user.getUserId())) ){
            throw new YouAreNotAdmin("방장만이 탈퇴시킬수있습니다");
        }

        //삭제대상 가져오기
        Long removeId = removeUserFromPostRequest.getRemoveId();
        log.info("userId:{}", removeId);
        if(removeId == null){
            throw new EntityNotFoundFromDB("정확한 id를 입력해주세요");
        }
        User removeUser = dataJpaUserRepository.findById(removeId)
                .orElseThrow(() -> new EntityNotFoundFromDB("삭제하려는 유저를 찾지못했습니다"));

        //삭제
        Post returnPost = postUserService.leavePost(post, removeUser);

        return new ApiResponse<>(HttpStatus.OK, true, returnPost.getPostId());
    }



    @GetMapping("/research")
    public HttpEntity<Page<ResearchPostResponse>> researchPost(@RequestBody ResearchRequest researchRequest,
                                                               Pageable pageable) {

        return new ResponseEntity<>(postService.researchPost(researchRequest, pageable), HttpStatus.OK);
    }

    @PostMapping("/{postId}/delete")
    public HttpEntity<ApiResponse<String>> deletePost(@PathVariable Long postId, @Login User user){
        if(user == null){
            throw new LoggedInUserNotFound("수정 중 로그인불량");
        }

        Post post = postService.getPost(postId)
                .orElseThrow(() -> new PostNotFoundFromDB("postId:" + postId + "not found"));

        String rtnValue = postService.deletePost(post, user);

        if(rtnValue.equals(DeleteConstants.DELETE_COMPLETE)){
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK,true, rtnValue), HttpStatus.OK);
        } else if (rtnValue.equals(DeleteConstants.YOU_CANT_DELETE)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, false, rtnValue), HttpStatus.BAD_REQUEST);
        } else if(rtnValue.equals(DeleteConstants.AFTER_START_TIME)){
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, false, rtnValue), HttpStatus.BAD_REQUEST);
        }
        return null;
    }


    @Data
    public static class PostAndComments{
        private PostResponse postResponse;
        private Page<ImageResponse> pagedImageResponseList;
        private Page<CommentResponse> pagedCommentResponseList;
    }

}
