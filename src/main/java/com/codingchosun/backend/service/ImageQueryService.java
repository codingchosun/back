package com.codingchosun.backend.service;

import com.codingchosun.backend.repository.image.DataJpaImageRepository;
import com.codingchosun.backend.response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQueryService {

    private final DataJpaImageRepository imageRepository;

    //이미지 목록 조회
    public Page<ImageResponse> getImages(Long postId, Pageable pageable) {
        return imageRepository.findByPost_PostId(postId, pageable).map(ImageResponse::new);
    }
}
