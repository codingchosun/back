package com.codingchosun.backend.component.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStore {

    @Value("${image.directory.path}")
    private String directoryPath;

    //여러 이미지 파일 저장
    public List<UploadFile> storeImages(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeImage(multipartFile));
            }
        }
        return storeFileResult;
    }

    //하나의 이미지 파일 저장
    public UploadFile storeImage(MultipartFile multipartFile) throws IOException {

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fullPath = getFullPath(storeFileName);
        log.info("파일 저장 경로 = {}", fullPath);

        multipartFile.transferTo(new File(fullPath));

        return new UploadFile(originalFilename, storeFileName);
    }

    //파일이름을 uuid 로 만들어서 중복 방지
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //파일이름에서 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    //파일포함한 경로
    private String getFullPath(String filename) {
        return directoryPath + File.separator + filename;
    }
}
