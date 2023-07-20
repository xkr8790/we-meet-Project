package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class MypagesEntity {
    private int index;
    private String email;
    private String content;
    private byte[] profileThumbnail;
    private String profileThumbnailMime;
    private Date createdAt;
    private Date updateAt;
    private String nickname;
    private String contact;
    private String registerBy;

    public int getIndex() {
        return index;
    }

    public MypagesEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MypagesEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MypagesEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public byte[] getProfileThumbnail() {
        return profileThumbnail;
    }

    public MypagesEntity setProfileThumbnail(byte[] profileThumbnail) {
        this.profileThumbnail = profileThumbnail;
        return this;
    }

    public String getProfileThumbnailMime() {
        return profileThumbnailMime;
    }

    public MypagesEntity setProfileThumbnailMime(String profileThumbnailMime) {
        this.profileThumbnailMime = profileThumbnailMime;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public MypagesEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public MypagesEntity setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public MypagesEntity setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public MypagesEntity setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getRegisterBy() {
        return registerBy;
    }

    public MypagesEntity setRegisterBy(String registerBy) {
        this.registerBy = registerBy;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MypagesEntity that = (MypagesEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
