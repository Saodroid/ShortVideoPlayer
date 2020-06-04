package com.rett.androidcouresfinalwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.rett.androidcouresfinalwork.model.VideoInfo;
import com.rett.androidcouresfinalwork.network.TiktokAPI;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: 王镖
 * @Date: 2020年6月4日 12点15分
 * @LastEditors: 王镖
 * @LastEditTime: 2020年6月4日 12点15分
 */
public class VideoListActivity extends AppCompatActivity {

    private RecyclerView rv_video_list;
    private VideoListAdapter itemAdapter;
    private LinearLayoutManager layoutManager;
    private List<VideoInfo> videoInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        // 接收来自MainActivity的数据
        Intent intent = getIntent();
        String list = intent.getStringExtra("videoInfos");
        // 将接收到的相应数据的格式转换成VideoInfo
        Gson gson = new Gson();
        List<LinkedTreeMap> maps = gson.fromJson(list, List.class);
        Log.d("videoInfos", String.valueOf(videoInfos));
        videoInfos = new LinkedList<>();
        if(maps != null) {
            for (LinkedTreeMap map: maps) {
                VideoInfo videoInfo = new VideoInfo(map);
                videoInfos.add(videoInfo);
            }
        }

        rv_video_list = findViewById(R.id.rv_video_list);
        rv_video_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_video_list.setLayoutManager(layoutManager);
        itemAdapter = new VideoListAdapter(this);
        rv_video_list.setAdapter(itemAdapter);
        itemAdapter.setVideoInfoList(videoInfos);
    }


}

