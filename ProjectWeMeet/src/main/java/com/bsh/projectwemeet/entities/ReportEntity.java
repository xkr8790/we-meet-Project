package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class ReportEntity {
    private int index;
    private int ArticleIndex;
    private String email;
    private Date createdAt;
    private boolean reportFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportEntity that = (ReportEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public ReportEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getArticleIndex() {
        return ArticleIndex;
    }

    public ReportEntity setArticleIndex(int articleIndex) {
        ArticleIndex = articleIndex;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ReportEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ReportEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public ReportEntity setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
        return this;
    }
}
