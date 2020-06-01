package com.rett.androidcouresfinalwork.model;

import com.google.gson.annotations.SerializedName;

public class VideoInfo {
    @SerializedName("_id")
    public String id;
    @SerializedName("feedurl")
    public String feedurl;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("likecount")
    public int likecount;
    @SerializedName("avatar")
    public String avatar;

    public int getLikecount() {
        return likecount;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public String getFeedurl() {
        return feedurl;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
