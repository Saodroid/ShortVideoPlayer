package com.rett.androidcouresfinalwork;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.rett.androidcouresfinalwork.model.VideoInfo;
import com.rett.androidcouresfinalwork.utils.MyVideoView;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final Context context;
    private List<VideoInfo> videoInfoList;
    private final ListItemClickListener listItemClickListener;

    public VideoAdapter(Context context, ListItemClickListener listener){
        this.context = context;
        listItemClickListener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_list_item, viewGroup, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);
        return videoViewHolder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        final VideoInfo videoInfo = videoInfoList.get(i);

        videoViewHolder.videoView.setVisibility(View.GONE);
        videoViewHolder.previewImage.setVisibility(View.VISIBLE);
        videoViewHolder.playButton.setVisibility(View.VISIBLE);
        videoViewHolder.loadingBar.setVisibility(View.GONE);
        videoViewHolder.description.setText(videoInfo.description);
        videoViewHolder.nickname.setText(videoInfo.nickname);

        Glide.with(videoViewHolder.videoItem)
                .load(videoInfo.avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(videoViewHolder.avatar);
        Glide.with(videoViewHolder.videoItem)
                .setDefaultRequestOptions(new RequestOptions().frame(3000000).centerCrop())
                .load(videoInfo.feedurl)
                .into(videoViewHolder.previewImage);
        videoViewHolder.videoView.setVideoPath(videoInfo.feedurl);

        Log.d("ViewHolder", String.valueOf(videoInfo.likecount));
        float temp = (float) videoInfo.likecount / 10000;
        if (temp >= 1){
            videoViewHolder.likeCount.setText(String.format("%.1fw", temp));
        }
        else{
            videoViewHolder.likeCount.setText(String.valueOf(videoInfo.likecount));
        }
    }

    @Override
    public int getItemCount() {
        return videoInfoList == null ? 0 : videoInfoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nickname;
        TextView description;
        TextView likeCount;
        TextView heart;
        ImageView avatar;
        ImageView previewImage;
        ImageView playButton;
        VideoView videoView;
        RelativeLayout videoItem;
        View loadingBar;
        boolean like;

        @SuppressLint("ClickableViewAccessibility")
        VideoViewHolder(@NonNull View itemView){
            super(itemView);
            like = false;
            videoView = itemView.findViewById(R.id.my_video_view);
            nickname = itemView.findViewById(R.id.video_id);
            description = itemView.findViewById(R.id.video_description);
            avatar = itemView.findViewById(R.id.avatar_img);
            likeCount = itemView.findViewById(R.id.like_count);
            heart = itemView.findViewById(R.id.heart);
            previewImage = itemView.findViewById(R.id.preview_image);
            videoItem = itemView.findViewById(R.id.video_item);
            playButton = itemView.findViewById(R.id.play_button);
            loadingBar = itemView.findViewById(R.id.loading_bar);
            GestureDetector gestureDetector;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    if (like){
                        heartClickAnimation();
                    }
                    else {
                        like = true;
                        heart.setBackground(context.getResources().getDrawable(R.drawable.ic_red_heart));
                        String likeCountStr = likeCount.getText().toString();
                        if (likeCountStr.charAt(likeCountStr.length() - 1) != 'w'){
                            likeCount.setText(String.valueOf(Integer.parseInt(likeCountStr) + 1));
                        }
                        heartClickAnimation();
                    }
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if (videoView.isPlaying()){
                        videoView.pause();
                        playButton.setVisibility(View.VISIBLE);
                    }
                    else{
                        videoView.start();
                        playButton.setVisibility(View.GONE);
                    }
                    return super.onSingleTapConfirmed(e);
                }
            });

            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    gestureDetector.onTouchEvent(motionEvent);
                    return true;
                }
            });

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String likeCountStr = likeCount.getText().toString();
                    if (like){
                        like = false;
                        heart.setBackground(context.getResources().getDrawable(R.drawable.ic_white_heart));
                        if (likeCountStr.charAt(likeCountStr.length() - 1) != 'w'){
                            likeCount.setText(String.valueOf(Integer.parseInt(likeCountStr) - 1));
                        }
                    }
                    else{
                        like = true;
                        heart.setBackground(context.getResources().getDrawable(R.drawable.ic_red_heart));
                        if (likeCountStr.charAt(likeCountStr.length() - 1) != 'w'){
                            likeCount.setText(String.valueOf(Integer.parseInt(likeCountStr) + 1));
                        }
                        heartClickAnimation();
                    }
                }
            });

            previewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    previewImage.setVisibility(View.GONE);
                    playButton.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    loadingBar.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show();
                }
            });

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    loadingBar.setVisibility(View.GONE);
                    videoView.requestFocus();
                    videoView.start();
                }
            });

            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "功能正在开发中，敬请期待！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "click video", Toast.LENGTH_SHORT).show();
            int clickedPosition = getAdapterPosition();
            if (listItemClickListener != null) {
                listItemClickListener.onListItemClick(clickedPosition);
            }
        }

        void heartClickAnimation(){
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(heart,
                    "scaleX", 1f, 2f);
            animatorX.setRepeatCount(1);
            animatorX.setInterpolator(new LinearInterpolator());
            animatorX.setRepeatMode(ValueAnimator.REVERSE);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(heart,
                    "scaleY", 1f, 2f);
            animatorX.setDuration(500);
            animatorY.setRepeatCount(1);
            animatorY.setInterpolator(new LinearInterpolator());
            animatorY.setRepeatMode(ValueAnimator.REVERSE);
            animatorY.setDuration(500);
            animatorX.start();
            animatorY.start();
        }

    }

    public void setVideoInfoList(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
