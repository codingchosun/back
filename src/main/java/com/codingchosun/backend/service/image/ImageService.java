package com.codingchosun.backend.service.image;

import com.codingchosun.backend.component.file.FileStore;
import com.codingchosun.backend.component.file.UploadFile;
import com.codingchosun.backend.domain.Image;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import com.codingchosun.backend.exception.file.FileStorageFailedException;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.ImageNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
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
            throw new FileStorageFailedException(ErrorCode.FILE_STORAGE_FAILED);
        }
    }

    public void deleteImage(Long postId, Long imageId, String loginId) {
        User user = findUserByLoginId(loginId);
        Post post = findPostById(postId);
        post.validateOwner(user);

        boolean removed = post.getImages()
                .removeIf(image -> image.getImageId()
                        .equals(imageId)
                );

        if (!removed) {
            throw new ImageNotFoundFromDB(ErrorCode.IMAGE_NOT_FOUND);
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND)
        );
    }

    private User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new UserNotFoundFromDB(ErrorCode.USER_NOT_FOUND)
        );
    }
}
