package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class LikeEntity {
    private int index;
    private int ArticleIndex;
    private String email;
    private Date createdAt;
    private boolean likeFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeEntity that = (LikeEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public LikeEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getArticleIndex() {
        return ArticleIndex;
    }

    public LikeEntity setArticleIndex(int articleIndex) {
        ArticleIndex = articleIndex;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public LikeEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public LikeEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isLikeFlag() {
        return likeFlag;
    }

    public LikeEntity setLikeFlag(boolean likeFlag) {
        this.likeFlag = likeFlag;
        return this;
    }
}
