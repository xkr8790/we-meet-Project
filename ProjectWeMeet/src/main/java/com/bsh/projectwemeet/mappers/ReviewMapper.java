package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {

    int insertReview(ReviewEntity reviewEntity);


    ReviewEntity[] selectReviewByIndex(@Param(value="articleIndex") int articleIndex);

    int deleteByReview(@Param(value = "index") int index);

    int updateByText(ReviewEntity reviewEntity);
}
