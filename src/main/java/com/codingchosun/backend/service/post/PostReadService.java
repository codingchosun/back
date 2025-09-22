package com.codingchosun.backend.service.post;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Image;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.dto.response.*;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.hashtag.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtag.DataJpaUserHashRepository;
import com.codingchosun.backend.repository.image.DataJpaImageRepository;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostReadService {

    private final DataJpaPostRepository postRepository;
    private final DataJpaHashtagRepository hashtagRepository;
    private final DataJpaUserHashRepository userHashRepository;
    private final DataJpaImageRepository imageRepository;

    //게시물 상세정보 조회
    public PostResponse getPostResponse(Long postId) {
        Post post = findPostById(postId);

        if (post.getStateCode().equals(StateCode.INACTIVE)) {
            throw new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND);
        }

        return PostResponse.from(post);
    }

    //비로그인 사용자용 게시물 목록 조회 및 추천 해시태그 반환
    public NoLoginPostsHashtagsResponse noLoginGetPosts(Pageable pageable) {
        List<HashtagDto> randomHashtags = hashtagRepository.findRandomHashtags(5).stream()
                .map(HashtagDto::from)
                .collect(Collectors.toList());

        Page<NoLoginPostsResponse> postsPage = postRepository.findAllActiveByOrderByCreatedAtDesc(pageable)
                .map(post -> NoLoginPostsResponse.from(post, findFirstImageUrl(post)));

        return new NoLoginPostsHashtagsResponse(postsPage, randomHashtags);
    }

    //로그인 사용자 추천 게시물 목록 조회, 관심 해시태그가 없을 경우 최신 게시물 목록 반환
    public LoginPostsHashtagResponse loginPostsRequests(String loginId, Pageable pageable) {
        List<Long> recommendHashtagsId = userHashRepository.findHashtagIdsByLoginId(loginId);

        Page<Post> postsPage = null;

        if (recommendHashtagsId.isEmpty()) {
            log.info("관심 해시태그가 없는 사용자[최신 게시물을 조회]: {}", loginId);
            postsPage = postRepository.findAllActiveByOrderByCreatedAtDesc(pageable);
        } else {
            log.info("[관심 해시태그 기반 추천 게시물을 조회]: {}, 추천 해시 태그: {}", loginId, recommendHashtagsId);
            postsPage = postRepository.findPostsByHashTagIdIn(recommendHashtagsId, pageable);
        }

        Page<LoginPostsResponse> responsePage = postsPage
                .map(post -> LoginPostsResponse.from(post, findFirstImageUrl(post)));

        List<HashtagDto> recommendHashtags = hashtagRepository.findAllById(recommendHashtagsId).stream()
                .map(HashtagDto::from)
                .collect(Collectors.toList());

        return new LoginPostsHashtagResponse(responsePage, recommendHashtags);
    }

    //검색어 게시물 조회
    public Page<SearchPostResponse> searchPost(String search, Pageable pageable) {

        if (!StringUtils.hasText(search)) {
            return postRepository.findAllActiveByOrderByCreatedAtDesc(pageable)
                    .map(post -> SearchPostResponse.from(post, findFirstImageUrl(post)));
        }

        List<String> title = new ArrayList<>();
        List<String> hash = new ArrayList<>();
        Arrays.stream(search.split(" "))
                .filter(StringUtils::hasText)
                .forEach(keyword -> {
                    if (keyword.startsWith("#")) {
                        hash.add(keyword);
                    } else {
                        title.add(keyword);
                    }
                });

        return postRepository.findPostsBySearchQuery(title, hash, pageable)
                .map(post -> SearchPostResponse.from(post, findFirstImageUrl(post)));
    }

    private String findFirstImageUrl(Post post) {
        return imageRepository.findFirstByPost(post)
                .map(Image::getUrl)
                .map(this::extractImageNameFromUrl)
                .orElse(null);
    }

    private String extractImageNameFromUrl(String url) {
        if (url == null) return null;
        String[] parts = url.split("/");
        return parts.length > 0 ? parts[parts.length - 1] : null;
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND)
        );
    }
}
