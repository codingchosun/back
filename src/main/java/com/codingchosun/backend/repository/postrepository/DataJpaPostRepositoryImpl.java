package com.codingchosun.backend.repository.postrepository;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.QPost;
import com.codingchosun.backend.domain.QPostHash;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class DataJpaPostRepositoryImpl implements DataJpaPostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Post> findPostsByHashTagId(List<Long> hashTagId, Pageable pageable) {
//        List<Post> contents = jpaQueryFactory.selectFrom(QPost.post)
//                .innerJoin(QPost.post)
//                .on(QPost.post.eq(QPostHash.postHash.post))
//                .where(QPostHash.postHash.hashtag.hashtagId.in(hashTagId))
//                .distinct()
//                .orderBy(QPost.post.postId.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//        JPAQuery<Long> countQuery = jpaQueryFactory
//                .select(QPost.post.count())
//                .from(QPost.post)
//                .innerJoin(QPost.post)
//                .on(QPost.post.eq(QPostHash.postHash.post))
//                .where(QPostHash.postHash.hashtag.hashtagId.in(hashTagId))
//                .distinct();
//
//        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);

            List<Post> contents = jpaQueryFactory.selectFrom(QPost.post)
                    .innerJoin(QPostHash.postHash)
                    .on(QPost.post.eq(QPostHash.postHash.post))
                    .where(QPostHash.postHash.hashtag.hashtagId.in(hashTagId))
                    .distinct()
                    .orderBy(QPost.post.postId.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            JPAQuery<Long> countQuery = jpaQueryFactory
                    .select(QPost.post.postId.countDistinct())
                    .from(QPost.post)
                    .innerJoin(QPostHash.postHash)
                    .on(QPost.post.eq(QPostHash.postHash.post))
                    .where(QPostHash.postHash.hashtag.hashtagId.in(hashTagId));

            return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);


    }
}
