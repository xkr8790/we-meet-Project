package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class ArticleEntity {
    private int index;
    private String email;
    private String title;
    private int category;
    private String content;
    private String addressPrimary;
    private String addressSecondary;
    private byte[] thumbnail;
    private String thumbnailMime;
    private int view;
    private Date createAt;
    private Date appointmentStartDate;
    private Date appointmentStartTime;
    private int limitPeople;
    private int participation;
    private int likeCount;
    private int report;
    private boolean isDeleted;
    private boolean isFinished;
    private int latitude;
    private int longitude;
    private String hashtag;
    private String clientIp;
    private String clientUa;

    public String getClientIp() {
        return clientIp;
    }

    public ArticleEntity setClientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public String getClientUa() {
        return clientUa;
    }

    public ArticleEntity setClientUa(String clientUa) {
        this.clientUa = clientUa;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity that = (ArticleEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public ArticleEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ArticleEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getCategory() {
        return category;
    }

    public ArticleEntity setCategory(int category) {
        this.category = category;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticleEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAddressPrimary() {
        return addressPrimary;
    }

    public ArticleEntity setAddressPrimary(String addressPrimary) {
        this.addressPrimary = addressPrimary;
        return this;
    }

    public String getAddressSecondary() {
        return addressSecondary;
    }

    public ArticleEntity setAddressSecondary(String addressSecondary) {
        this.addressSecondary = addressSecondary;
        return this;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public ArticleEntity setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getThumbnailMime() {
        return thumbnailMime;
    }

    public ArticleEntity setThumbnailMime(String thumbnailMime) {
        this.thumbnailMime = thumbnailMime;
        return this;
    }

    public int getView() {
        return view;
    }

    public ArticleEntity setView(int view) {
        this.view = view;
        return this;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public ArticleEntity setCreateAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public Date getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public ArticleEntity setAppointmentStartDate(Date appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
        return this;
    }

    public Date getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public ArticleEntity setAppointmentStartTime(Date appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
        return this;
    }

    public int getLimitPeople() {
        return limitPeople;
    }

    public ArticleEntity setLimitPeople(int limitPeople) {
        this.limitPeople = limitPeople;
        return this;
    }

    public int getParticipation() {
        return participation;
    }

    public ArticleEntity setParticipation(int participation) {
        this.participation = participation;
        return this;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public ArticleEntity setLikeCount(int likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public int getReport() {
        return report;
    }

    public ArticleEntity setReport(int report) {
        this.report = report;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public ArticleEntity setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public ArticleEntity setFinished(boolean finished) {
        isFinished = finished;
        return this;
    }

    public int getLatitude() {
        return latitude;
    }

    public ArticleEntity setLatitude(int latitude) {
        this.latitude = latitude;
        return this;
    }

    public int getLongitude() {
        return longitude;
    }

    public ArticleEntity setLongitude(int longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getHashtag() {
        return hashtag;
    }

    public ArticleEntity setHashtag(String hashtag) {
        this.hashtag = hashtag;
        return this;
    }
}
