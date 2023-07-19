package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.entities.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {

    ArticleEntity[] articleAll();

    int insertReview(ReviewEntity reviewEntity);

    ReviewEntity[] selectAll();
    ReviewEntity[] selectArticleIndex(@Param(value="articleIndex") int articleIndex);

    ReviewEntity selectReviewByIndex(@Param(value="index") int index);


    int deleteByReview(@Param(value="index") int index);


    Double avgStar(@Param(value="articleIndex") int articleIndex);
//    int updateByText(ReviewEntity reviewEntity);



}
