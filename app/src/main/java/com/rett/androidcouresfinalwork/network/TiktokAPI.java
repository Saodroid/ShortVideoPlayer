package com.rett.androidcouresfinalwork.network;

import com.rett.androidcouresfinalwork.model.VideoInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TiktokAPI {
    @GET("api/invoke/video/invoke/video")
    Call<VideoInfo[]> getVideoInfo();
}
