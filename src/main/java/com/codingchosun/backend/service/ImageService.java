package com.codingchosun.backend.service;

import com.codingchosun.backend.component.file.FileStore;
import com.codingchosun.backend.domain.Image;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.repository.imagerepository.DataJpaImageRepository;
import com.codingchosun.backend.response.ImageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final DataJpaImageRepository dataJpaImageRepository;
    private final FileStore fileStore;

    public Page<ImageResponse> getImageURLList(Pageable pageable, Long postId){
        return dataJpaImageRepository.findByPost_PostId(postId, pageable).map(ImageResponse::new);
    }

    //여러개의 파일을 받아서 지정된 경로에 저장, 리턴값으로는 저장한 파일개수 반환
    public int uploadImages(List<MultipartFile> multipartFiles, Post post) {

        List<String> savedFiles;    //파일의 본래 이름이 아닌 저장 됐을때의 이름
        int count = 0;              //저장한 파일 개수

        try {
            savedFiles = fileStore.storeImages(multipartFiles);
        } catch (IOException e) {
            throw new RuntimeException(e);  //파일 저장 중 실패
        }

        if(savedFiles.isEmpty()){
            log.info("사진 없음");  //MultipartFile이 비었을 경우
        }

        for (String savedFile : savedFiles) {
            Image image = new Image();
            image.setPost(post);
            image.setUrl(savedFile);
            image.setName("none");    //일단 생략

            dataJpaImageRepository.save(image); //저장
            count++;
        }

        return  count;
    }


}
