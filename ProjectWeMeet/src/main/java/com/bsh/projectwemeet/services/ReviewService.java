package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.entities.ReviewEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.ReviewResult;
import com.bsh.projectwemeet.mappers.ArticleMapper;
import com.bsh.projectwemeet.mappers.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

@Service
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final ArticleMapper articleMapper;

    @Autowired
    public ReviewService(ReviewMapper reviewMapper, ArticleMapper articleMapper) {
        this.reviewMapper = reviewMapper;
        this.articleMapper = articleMapper;
    }

// 리뷰 insert
    public ReviewResult reviewWrite(HttpServletRequest request, ReviewEntity reviewEntity, HttpSession session) {

        UserEntity loginUser = (UserEntity) session.getAttribute("user");
//       게시물의 작성자 & 참여자가 아닐경우
        if (1 != reviewMapper.selectParticipant(reviewEntity.getArticleIndex(), loginUser.getEmail()) &&
                1 != reviewMapper.selectArticleWriter(reviewEntity.getArticleIndex(), loginUser.getEmail())) {
            return ReviewResult.FAILURE_EXCEPTION;
        }
        reviewEntity.setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"));
        return this.reviewMapper.insertReview(reviewEntity) > 0
                ? ReviewResult.SUCCESS
                : ReviewResult.FAILURE;
    }

// 데이터 베이스의 리뷰 select
    public ReviewEntity[] getAll() {
        return this.reviewMapper.selectAll();
    }

    // 리뷰 평점 계산
    public Double avgStar(int articleIndex) {
        Double averageScore = reviewMapper.avgStar(articleIndex);
        if (averageScore != null) {
            return Math.round(averageScore * 10.0) / 10.0;
        }
        return null;
    }

    //    리뷰 작성자 프로필 select
    public ProfileEntity readReviewProfile(int index) {
        ReviewEntity reviews = this.reviewMapper.selectEmail(index);
        ProfileEntity article = this.reviewMapper.selectProfileImage(reviews.getEmail());
        return article;
    }

    // 리뷰 삭제
    public boolean deleteByIndex(int index) {
        return this.reviewMapper.deleteByReview(index) > 0;
    }


//    public ReviewEntity[] selectAll(int articleIndex) {
//        return this.reviewMapper.selectArticleIndex(articleIndex);
//    }



}
