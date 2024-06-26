package com.codingchosun.backend.service;

import com.codingchosun.backend.domain.*;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaHashtagRepository;
import com.codingchosun.backend.repository.hashtagrepository.DataJpaUserHashRepository;
import com.codingchosun.backend.repository.userrepository.DataJpaUserRepository;
import com.codingchosun.backend.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserUpdateService {

    private DataJpaUserRepository userRepository;
    private DataJpaUserHashRepository userHashRepository;
    private DataJpaHashtagRepository hashtagRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserUpdateService(DataJpaUserRepository userRepository, DataJpaUserHashRepository userHashRepository, DataJpaHashtagRepository hashtagRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userHashRepository = userHashRepository;
        this.hashtagRepository = hashtagRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateUser(User user, UserUpdateRequest updateRequest) {
        user = userRepository.findById(user.getUserId()).orElseThrow(()
                -> new IllegalArgumentException("해당하는 아이디가 없습니다 id : "));
        List<String> hashList = updateRequest.getHashList();
        String encodedPassword = passwordEncoder.encode(updateRequest.getPassword());
        updateRequest = UserUpdateRequest.builder()
                .password(updateRequest.getPassword() != null ? encodedPassword : user.getPassword())
                .email(updateRequest.getEmail() != null ? updateRequest.getEmail() : user.getEmail())
                .genderCode(updateRequest.getGenderCode() != null ? updateRequest.getGenderCode() : user.getGenderCode())
                .nickname(updateRequest.getNickname() != null ? updateRequest.getNickname() : user.getNickname())
                .introduction(updateRequest.getIntroduction() != null ? updateRequest.getIntroduction() : user.getIntroduction())
                .build();
        updateUserHashtag(user, hashList);
        user.setUpdateRequest(updateRequest);
        userRepository.save(user);
    }

    public void updateUserHashtag(User user, List<String> hashList) {
        //해시태그 수정하기위해 그냥 이 유저의 userhash를 전부삭제
        List<UserHash> userHashes = userHashRepository.findHashtagsByUser_UserId(user.getUserId());
        userHashRepository.deleteAll(userHashes);
        log.info("hashList = {}", hashList);
        //새롭게 등록할 해시태그의 개수만큼 반복문
        for (String hashtagString : hashList) {
            Optional<Hashtag> optionalHashtag = hashtagRepository.findByHashtagName(hashtagString);
            Hashtag hashtag;    //hashtag null확인 하려고 옵셔널 처리

            if (optionalHashtag.isPresent()) {  //이미 hashtag테이블에 등록된 해시태그라면
                hashtag = optionalHashtag.get(); //등록된 hashtag 가져옴
                UserHash existUserHash = userHashRepository.findHashtagsByHashtag_HashtagIdAndUser_UserId(hashtag.getHashtagId(), user.getUserId());
                //hashtagid, userid로 똑같은 userhash가 있는지 검사하기위한 코드
                if (existUserHash == null) {
                    UserHash userHash = new UserHash();
                    userHash.setUser(user);
                    userHash.setHashtag(hashtag);
                    userHashRepository.save(userHash);
                    //이미존재하는 똑같은 userhash가 없으니 새로운 userhash 저장
                }
            } else {  //이미등록된hashtag, 이미등록된userhash가 아닌경우
                //해시태그, 유저해시가 둘다없음 (해시태그도 유저해시도 둘다 새로등록해야한다는 뜻)
                hashtag = new Hashtag();
                hashtag.setHashtagName(hashtagString);
                hashtag = hashtagRepository.save(hashtag);

                UserHash userHash = new UserHash();
                userHash.setUser(user);
                userHash.setHashtag(hashtag);

                userHashRepository.save(userHash);
            }
        }
        userRepository.save(user);
    }
}