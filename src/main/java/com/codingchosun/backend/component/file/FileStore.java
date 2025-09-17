package com.codingchosun.backend.component.file;



import com.codingchosun.backend.repository.image.DataJpaImageRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//파일 저장 기능
@Component
@Slf4j
public class FileStore {

    private final ApplicationContext applicationContext;
    private final DataJpaImageRepository dataJpaImageRepository;
    private String DIRECTORY_PATH;

    @Autowired
    public FileStore(ApplicationContext applicationContext, DataJpaImageRepository dataJpaImageRepository) {
        this.applicationContext = applicationContext;
        this.dataJpaImageRepository = dataJpaImageRepository;
    }


    //디렉토리 설정
    @PostConstruct
    public void init() {
        Environment env = applicationContext.getEnvironment();
        DIRECTORY_PATH = env.getProperty("image.directory.path");
        log.info("path = {}",DIRECTORY_PATH);   //파일 경로확인용
    }

    //이미지 저장 메서드 사진 여러개 가능
    public List<String> storeImages(List<MultipartFile> multipartFiles) throws IOException {
        List<String> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeImage(multipartFile));
            }
        }
        return storeFileResult; //저장된 파일들의 이름 리스트
    }

    public String  storeImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fullPath = getFullPath(storeFileName);
        log.info("저장되는 경로 = {}", fullPath);
        multipartFile.transferTo(new File(fullPath));

        return fullPath;
    }

    //파일이름을 uuid로 만들어서 중복 방지
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자 리턴(jpeg일수도 있고, png일수도있음)
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    //파일포함한 경로
    public String getFullPath(String filename) {
        return DIRECTORY_PATH + filename;
    }
}
