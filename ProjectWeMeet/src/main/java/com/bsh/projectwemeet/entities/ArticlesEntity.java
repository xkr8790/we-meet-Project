package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class ArticlesEntity {
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
    private Date appointmentEndDate;
    private Date appointmentStartTime;
    private Date appointmentEndTime;
    private int limitPeople;
    private int participation;
    private int likeCount;
    private int report;
    private boolean isDeleted;
    private boolean isFinished;
    private int latitude;
    private int longitude;
    private String hashtag;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticlesEntity that = (ArticlesEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public ArticlesEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ArticlesEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticlesEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getCategory() {
        return category;
    }

    public ArticlesEntity setCategory(int category) {
        this.category = category;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticlesEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAddressPrimary() {
        return addressPrimary;
    }

    public ArticlesEntity setAddressPrimary(String addressPrimary) {
        this.addressPrimary = addressPrimary;
        return this;
    }

    public String getAddressSecondary() {
        return addressSecondary;
    }

    public ArticlesEntity setAddressSecondary(String addressSecondary) {
        this.addressSecondary = addressSecondary;
        return this;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public ArticlesEntity setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getThumbnailMime() {
        return thumbnailMime;
    }

    public ArticlesEntity setThumbnailMime(String thumbnailMime) {
        this.thumbnailMime = thumbnailMime;
        return this;
    }

    public int getView() {
        return view;
    }

    public ArticlesEntity setView(int view) {
        this.view = view;
        return this;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public ArticlesEntity setCreateAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }

    public Date getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public ArticlesEntity setAppointmentStartDate(Date appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
        return this;
    }

    public Date getAppointmentEndDate() {
        return appointmentEndDate;
    }

    public ArticlesEntity setAppointmentEndDate(Date appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
        return this;
    }

    public Date getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public ArticlesEntity setAppointmentStartTime(Date appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
        return this;
    }

    public Date getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public ArticlesEntity setAppointmentEndTime(Date appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
        return this;
    }

    public int getLimitPeople() {
        return limitPeople;
    }

    public ArticlesEntity setLimitPeople(int limitPeople) {
        this.limitPeople = limitPeople;
        return this;
    }

    public int getParticipation() {
        return participation;
    }

    public ArticlesEntity setParticipation(int participation) {
        this.participation = participation;
        return this;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public ArticlesEntity setLikeCount(int likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public int getReport() {
        return report;
    }

    public ArticlesEntity setReport(int report) {
        this.report = report;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public ArticlesEntity setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public ArticlesEntity setFinished(boolean finished) {
        isFinished = finished;
        return this;
    }

    public int getLatitude() {
        return latitude;
    }

    public ArticlesEntity setLatitude(int latitude) {
        this.latitude = latitude;
        return this;
    }

    public int getLongitude() {
        return longitude;
    }

    public ArticlesEntity setLongitude(int longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getHashtag() {
        return hashtag;
    }

    public ArticlesEntity setHashtag(String hashtag) {
        this.hashtag = hashtag;
        return this;
    }
}
