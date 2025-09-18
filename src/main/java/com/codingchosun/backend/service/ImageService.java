package com.codingchosun.backend.service;

import com.codingchosun.backend.component.file.FileStore;
import com.codingchosun.backend.component.file.UploadFile;
import com.codingchosun.backend.domain.Image;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.FileException;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.ImageNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import com.codingchosun.backend.repository.user.DataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final DataJpaPostRepository postRepository;
    private final DataJpaUserRepository userRepository;
    private final FileStore fileStore;

    //여러개의 파일을 받아서 지정된 경로에 저장
    public void uploadImages(Long postId, String loginId, List<MultipartFile> multipartFiles) {
        try {
            User user = findUserByLoginId(loginId);
            Post post = findPostById(postId);
            post.validateOwner(user);

            List<UploadFile> uploadFiles = fileStore.storeImages(multipartFiles);

            for (UploadFile file : uploadFiles) {
                Image image = new Image(file.getStoreFileName(), file.getUploadFileName(), post);
                post.addImage(image);
            }
        } catch (IOException e) {
            throw new FileException("이미지 저장 실패");
        }
    }

    //이미지 삭제
    public void deleteImage(Long postId, Long imageId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);
        post.validateOwner(user);

        boolean removed = post.getImages()
                .removeIf(image -> image.getImageId()
                        .equals(imageId)
                );

        if (!removed) {
            throw new ImageNotFoundFromDB("해당 게시물에 존재하지 않는 이미지입니다: " + imageId);
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB("해당 게시물을 찾지 못하였습니다" + postId)
        );
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new EntityNotFoundFromDB("아이디에 해당하는 유저를 찾지 못했습니다" + loginId)
        );
    }
}
