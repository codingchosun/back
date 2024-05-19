INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-15', 80, 1, 'wqe', 'qwe', 'qwe', '123', 'kim', 'dg', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-15', 80, 2, 'wqe', 'qwe', 'qwe', '123', 'kim', 'dg', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-15', 80, 3, 'wqe', 'qwe', 'qwe', '123', 'kim', 'dg', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-15', 80, 4, 'wqe', 'qwe', 'qwe', '123', 'kim', 'dg', 'MALE');
INSERT INTO user (birth, score, state, email, introduction, login_id, name, nickname, password, gender_code) VALUES ('2024-05-15', 80, 5, 'wqe', 'qwe', 'qwe', '123', 'kim', 'dg', 'MALE');

INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '2024-01-13 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 1, 'asd', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '1542-02-14 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 2, 'qwe', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '2008-03-15 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 3, 'zxc', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '1990-04-16 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 4, 'rty', 'qweqe');
INSERT INTO post (state_code, view_count, created_at, end_time, start_time, user_id, content, title) VALUES (1, 21, '2000-05-17 15:40:21.000000', '2024-05-12 15:40:28.000000', '2024-05-12 15:40:31.000000', 5, 'yui', 'czczx');

INSERT INTO post_user (post_id, user_id) VALUES (1, 1);
INSERT INTO post_user (post_id, user_id) VALUES (2, 1);
INSERT INTO post_user (post_id, user_id) VALUES (3, 1);
INSERT INTO post_user (post_id, user_id) VALUES (4, 1);
INSERT INTO post_user (post_id, user_id) VALUES (5, 1);


INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (1 ,'#광주광역시');
 INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (2 ,'#지원1동');
INSERT INTO hashtag (hashtag_id, hashtag_name) VALUES  (3 ,'#소태역');
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