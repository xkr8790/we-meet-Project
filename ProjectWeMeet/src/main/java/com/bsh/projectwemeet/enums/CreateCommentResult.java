package com.bsh.projectwemeet.enums;

public enum CreateCommentResult {
    FAILURE, // 실패
    FAILURE_NOT_LOGIN,
    SUCCESS, // 성공(작성자가 아닐 때)
    SUCCESS_SAME // 성공(작성자 일 때)
}
