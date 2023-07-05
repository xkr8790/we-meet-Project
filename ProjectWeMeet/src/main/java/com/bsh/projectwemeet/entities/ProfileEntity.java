package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class ProfileEntity {
    private int index;
    private String email;
    private String content;
    private byte[] profileThumbnail;
    private String profileThumbnailMime;
    private Date createdAt;
    private Date updatedAt;
    private String nickname;
    private String contact;
    private String registerBy;

    public String getNickname() {
        return nickname;
    }

    public ProfileEntity setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public ProfileEntity setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getRegisterBy() {
        return registerBy;
    }

    public ProfileEntity setRegisterBy(String registerBy) {
        this.registerBy = registerBy;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ProfileEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProfileEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ProfileEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public byte[] getProfileThumbnail() {
        return profileThumbnail;
    }

    public ProfileEntity setProfileThumbnail(byte[] profileThumbnail) {
        this.profileThumbnail = profileThumbnail;
        return this;
    }

    public String getProfileThumbnailMime() {
        return profileThumbnailMime;
    }

    public ProfileEntity setProfileThumbnailMime(String profileThumbnailMime) {
        this.profileThumbnailMime = profileThumbnailMime;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ProfileEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public ProfileEntity setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileEntity that = (ProfileEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
