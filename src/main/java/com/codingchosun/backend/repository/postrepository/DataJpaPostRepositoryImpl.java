package com.codingchosun.backend.repository.postrepository;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.QHashtag;
import com.codingchosun.backend.domain.QPost;
import com.codingchosun.backend.domain.QPostHash;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.codingchosun.backend.domain.QHashtag.hashtag;
import static com.codingchosun.backend.domain.QPost.post;
import static com.codingchosun.backend.domain.QPostHash.postHash;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class DataJpaPostRepositoryImpl implements DataJpaPostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Post> findPostsByHashTagId(List<Long> hashTagId, Pageable pageable) {

            List<Post> contents = jpaQueryFactory.selectFrom(post)
                    .innerJoin(postHash)
                    .on(post.eq(postHash.post))
                    .where(postHash.hashtag.hashtagId.in(hashTagId)
                    .and(post.stateCode.eq(StateCode.ACTIVE)))
                    .distinct()
                    .orderBy(post.createdAt.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
            JPAQuery<Long> countQuery = jpaQueryFactory
                    .select(post.postId.countDistinct())
                    .from(post)
                    .innerJoin(postHash)
                    .on(post.eq(postHash.post))
                    .where(postHash.hashtag.hashtagId.in(hashTagId));

            return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<Post> findPostsByResearchQuery(List<String> titleQuery, List<String> hashQuery, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        for (String t : titleQuery) {
            builder.and(post.title.contains(t));
        }

        if (hashQuery != null && !hashQuery.isEmpty()) {
            for (String h : hashQuery) {
                BooleanExpression hashExpression = JPAExpressions
                        .selectOne()
                        .from(postHash)
                        .innerJoin(postHash.hashtag, hashtag)
                        .where(postHash.post.eq(post)
                                .and(hashtag.hashtagName.eq(h))
                                .and(post.stateCode.eq(StateCode.ACTIVE)))
                        .exists();
                builder.and(hashExpression); // 모든 해시태그 조건을 and로 결합
            }
        }

        List<Post> contents = jpaQueryFactory.selectFrom(post)
                .innerJoin(postHash).on(post.postId.eq(postHash.post.postId))
                .innerJoin(postHash.hashtag, hashtag)
                .where(builder)
                .distinct()
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.postId.countDistinct())
                .from(post)
                .innerJoin(postHash).on(post.postId.eq(postHash.post.postId))
                .innerJoin(postHash.hashtag, hashtag)
                .where(builder);

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);


    }
}
