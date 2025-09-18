package com.codingchosun.backend.service.image;

import com.codingchosun.backend.dto.response.ImageResponse;
import com.codingchosun.backend.repository.image.DataJpaImageRepository;
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

    public Page<ImageResponse> getImages(Long postId, Pageable pageable) {
        return imageRepository.findByPost_PostId(postId, pageable).map(ImageResponse::new);
    }
}
