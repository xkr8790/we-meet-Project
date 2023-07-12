package com.bsh.projectwemeet.enums;

public enum CreateCommentResult {
    FAILURE, // 실패
    FAILURE_NOT_LOGIN, //로그인하지 않았을 때
    SUCCESS, // 성공(관리자일 때)
    SUCCESS_SAME // 성공(작성자 일 때)
}
