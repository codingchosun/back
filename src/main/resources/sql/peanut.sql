INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-23', 80, 'ACTIVE', 'wqe@naver.com', '나는 개똥벌레', 'id1111', 'pepsi', 'apple', 'pwd1111', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-02', 24, 'ACTIVE', 'sdfsdf@gmail.com', '난 바보입니다', 'id2222', 'nunuandwillump', 'banana', 'pwd2222', 'FEMALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-01', 53, 'ACTIVE', 'asdas@daum.net', '전 천재입니다.', 'id3333', 'con', 'kiwi', 'pwd3333', 'FEMALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-23', 80, 'ACTIVE', 'wqe22@naver.com', '나는 개똥벌레', 'id4444', 'pepsi', 'mango', 'pwd4444', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-02', 24, 'ACTIVE', 'sdfsdf44@gmail.com', '난 바보입니다', 'id5555', 'nunuandwillump', 'dragonfruits', 'pwd5555', 'FEMALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-01', 53, 'ACTIVE', 'asdas13@daum.net', '전 천재입니다.', 'id6666', 'con', 'melon', 'pwd6666', 'FEMALE');


INSERT INTO hashtag (hashtag_name) VALUES  ('#푸바오');
INSERT INTO hashtag (hashtag_name) VALUES  ('#한글태그');
INSERT INTO hashtag (hashtag_name) VALUES  ('#서울');
INSERT INTO hashtag (hashtag_name) VALUES  ('#리자몽');
INSERT INTO hashtag (hashtag_name) VALUES  ('#광주광역시');
INSERT INTO hashtag (hashtag_name) VALUES  ('#지원1동');
INSERT INTO hashtag (hashtag_name) VALUES  ('#소태역');

INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 1);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 2);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 3);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (2 , 2);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (2 , 3);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (2 , 4);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (3 , 3);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (3 , 4);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (3 , 5);

INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES ('ACTIVE', 1, '2024-05-12 15:40:21.000000', '2024-07-13 15:40:31.000000', '2024-07-12 15:40:31.000000', 1, 'first content', 'first post');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES ('ACTIVE', 1, '2024-05-12 15:40:21.000000', '2024-07-14 15:40:31.000000', '2024-07-13 15:40:31.000000', 2, 'second content', 'second post');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES ('ACTIVE', 1, '2024-05-12 15:40:21.000000', '2024-07-15 15:40:31.000000', '2024-07-14 15:40:31.000000', 3, 'third content', 'third post');

INSERT INTO post_hash (post_id, hashtag_id) VALUES  (1 , 1);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (1 , 2);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (1 , 3);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (2 , 3);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (2 , 4);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (2 , 5);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (3 , 5);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (3 , 6);
INSERT INTO post_hash (post_id, hashtag_id) VALUES  (3 , 7);

INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (1, 2, 'first comment','2024-05-13 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (1, 3, 'second comment','2024-05-14 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (1, 2, 'third comment','2024-05-15 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (2, 3, 'first comment','2024-05-13 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (2, 4, 'second comment','2024-05-14 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (2, 5, 'third comment','2024-05-15 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (3, 3, 'first comment','2024-05-13 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (3, 4, 'second comment','2024-05-14 15:40:21.000000' );
INSERT INTO comment (post_id, user_id, content, created_at) VALUES  (3, 5, 'third comment','2024-05-15 15:40:21.000000' );

INSERT INTO image(post_id, url, name) VALUES  (1,'/first/sample/path', 'apple');
INSERT INTO image(post_id, url, name) VALUES  (1,'/second/sample/path', 'banana');
INSERT INTO image(post_id, url, name) VALUES  (2,'/first/sample/path', 'apple');
INSERT INTO image(post_id, url, name) VALUES  (2,'/second/sample/path', 'banana');
INSERT INTO image(post_id, url, name) VALUES  (3,'/first/sample/path', 'apple');
INSERT INTO image(post_id, url, name) VALUES  (3,'/second/sample/path', 'banana');