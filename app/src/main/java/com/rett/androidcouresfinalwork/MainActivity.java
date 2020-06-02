package com.rett.androidcouresfinalwork;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.rett.androidcouresfinalwork.model.VideoInfo;
import com.rett.androidcouresfinalwork.network.TiktokAPI;
import com.rett.androidcouresfinalwork.utils.MyVideoView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements VideoAdapter.ListItemClickListener{
    private ViewPager2 videoPager;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPager = findViewById(R.id.video_pager);
        videoAdapter = new VideoAdapter(this, this);
        getData();

        videoPager.setAdapter(videoAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyVideoView.goOnPlayOnResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        MyVideoView.releaseAllVideos();
    }


    private void getData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TiktokAPI tiktokAPI = retrofit.create(TiktokAPI.class);
        tiktokAPI.getVideoInfo().enqueue(new Callback<List<VideoInfo>>() {
            @Override
            public void onResponse(Call<List<VideoInfo>> call, Response<List<VideoInfo>> response) {
                if(response.body() != null){
                    List<VideoInfo> videoInfos = response.body();
                    Log.d("retrofit", videoInfos.toString());
                    if(videoInfos.size() != 0){
                        videoAdapter.setVideoInfoList(videoInfos);
                        videoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoInfo>> call, Throwable t) {
                Log.d("retrofit", "on failure");
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        return;
    }
}

