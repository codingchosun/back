INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-15', 80, 1, 'sldkj@gmail.com', '저는 마루쉐', 'id44444','포스텍', '토트넘 감독', 'ToTToTToT', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-20', 80, 1, 'alsjnf@naver.com', '전 금태양', 'id555555','사비', '잘 모름 알아서 찾아', '123231', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-12', 80, 1, 'asijvn@daum.net', '난 공주얌', 'id66666','클롭', '잘가라', 'liverpool', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-11', 80, 1, 'zncoij@gmail.com', '콜라 중독자', 'id777777','안첼로티', '바셀 짤렸나???', '14242411', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-21', 80, 1, 'adoijcv@daum.net', '라면 먹다 비만됨', 'id888888','김정균', '나혼자 롤감독이다', 'sktt1감독', 'MALE');

INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '2024-01-13 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 1, 'asd', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '1542-02-14 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 2, 'qwe', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '2008-03-15 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 3, 'zxc', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '1990-04-16 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 4, 'rty', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '2000-05-17 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 5, 'yui', 'czczx');

INSERT INTO post_user (post_id, user_id) VALUES (1, 1);
INSERT INTO post_user (post_id, user_id) VALUES (2, 1);
INSERT INTO post_user (post_id, user_id) VALUES (3, 1);
INSERT INTO post_user (post_id, user_id) VALUES (5, 1);
INSERT INTO post_user (post_id, user_id) VALUES (4, 1);
INSERT INTO post_user (post_id, user_id) VALUES (4, 2);
INSERT INTO post_user (post_id, user_id) VALUES (4, 3);
INSERT INTO post_user (post_id, user_id) VALUES (4, 6);

# INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (1 ,'#광주광역시');
# INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (2 ,'#지원1동');
# INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (3 ,'#소태역');
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 1);;
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 2);
INSERT INTO user_hash (user_id, hashtag_id) VALUES  (1 , 3);
INSERT INTO template (score, content) VALUES (2, '이 집 잘하네');
INSERT INTO template (score, content) VALUES (10, '밥경찰');
INSERT INTO template (score, content) VALUES (33, '재미없어요');


INSERT INTO validate(template_id, from_user_id, to_user_id, post_id)
VALUES (1, 2, 1, 1);
INSERT INTO validate(template_id, from_user_id, to_user_id, post_id)
VALUES (2, 3, 1, 2);
INSERT INTO validate(template_id, from_user_id, to_user_id, post_id)
VALUES (3, 4, 1, 3);