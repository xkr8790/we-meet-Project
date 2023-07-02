package com.bsh.projectwemeet.enums;

public enum CreateArticleResult {
    FAILURE, // 실패
    FAILURE_MISSING_FIELDS, // 입력값 누락
    FAILURE_INVALID_FIELDS, // 모임시간이 현재 시간보다 과거인 경우
    FAILURE_EXCEEDED_LIMIT, // 제한 인원을 초과했을 때
    FAILURE_UNSUPPORTED_CATEGORY, // 지원하지 않는 카테고리 선택
    FAILURE_INVALID_HASHTAG, // 해시태그에 특수문자가 들어간 경우
    SUCCESS // 성공
}