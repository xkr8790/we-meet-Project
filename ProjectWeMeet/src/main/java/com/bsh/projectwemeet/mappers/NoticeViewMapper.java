package com.bsh.projectwemeet.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface NoticeViewMapper {


    int deleteArticleByIndex(@Param(value = "index")int index);

}
