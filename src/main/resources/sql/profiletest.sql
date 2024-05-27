#id 10000유저 프로필 내용 읽기

INSERT INTO user (user_id, birth, score, state, email, introduction, login_id, name, nickname, password, gender_code)
VALUES (10000,'2000-05-15', 50, 1, 'profiletest@gmail.com', '프로필내용테스트', 'id_profile','이름프로필테스트', '닉네임프로필테스트', 'pw_profile', 'MALE');

INSERT INTO user (user_id, birth, score, state, email, introduction, login_id, name, nickname, password, gender_code)
VALUES (9999,'2001-05-15', 50, 1, 'profiletest_1@gmail.com', '프로필내용테스트1', 'id_profile1','프로필템플릿1', '프로필닉네임1', 'pw_profile1', 'MALE');
INSERT INTO user (user_id, birth, score, state, email, introduction, login_id, name, nickname, password, gender_code)
VALUES (9998,'2002-05-15', 50, 1, 'profiletest_2@gmail.com', '프로필내용테스트2', 'id_profile2','프로필템플릿2', '프로필닉네임2', 'pw_profile2', 'MALE');
INSERT INTO user (user_id, birth, score, state, email, introduction, login_id, name, nickname, password, gender_code)
VALUES (9997,'2003-05-15', 50, 1, 'profiletest_3@gmail.com', '프로필내용테스트3', 'id_profile3','프로필템플릿3', '프로필닉네임3', 'pw_profile3', 'MALE');


 INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (9999 ,'#광주광역시');
 INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (9998 ,'#지원1동');
 INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (9997 ,'#소태역');


INSERT INTO user_hash (user_id, hashtag_id) VALUES  (10000 , 9999);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (10000 , 9998);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (10000 , 9997);

INSERT INTO template (template.template_id, score, content) VALUES (9999, 10, '템플릿1');
INSERT INTO template (template.template_id, score, content) VALUES (9998, 20, '템플릿2');
INSERT INTO template (template.template_id, score, content) VALUES (9997, 30, '템플릿3');


INSERT INTO post (post_id ,state_code, view_count, created_at, end_time, start_time, user_id, content, title)
VALUES (9999, 'ACTIVE', 21, '2024-01-13 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 1, 'asd', 'qweqe');
INSERT INTO post (post_id , state_code, view_count, created_at, end_time, start_time, user_id, content, title)
VALUES (9998, 'ACTIVE', 21, '1542-02-14 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 2, 'qwe', 'qweqe');
INSERT INTO post (post_id , state_code, view_count, created_at, end_time, start_time, user_id, content, title)
VALUES (9997, 'ACTIVE', 21,  '2008-03-15 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 3, 'zxc', 'qweqe');

INSERT INTO validate(template_id, from_user_id, to_user_id, post_id)
VALUES (9999, 9999, 10000, 9999);
INSERT INTO validate(template_id, from_user_id, to_user_id, post_id)
VALUES (9998, 9998, 10000, 9998);
INSERT INTO validate(template_id, from_user_id, to_user_id, post_id)
VALUES (9997, 9997, 10000, 9997);

