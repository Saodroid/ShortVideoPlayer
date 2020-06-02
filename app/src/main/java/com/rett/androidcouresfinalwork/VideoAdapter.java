package com.rett.androidcouresfinalwork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        final VideoInfo videoInfo = videoInfoList.get(i);

        videoViewHolder.description.setText(videoInfo.description);
        videoViewHolder.nickname.setText(videoInfo.nickname);
        Log.d("ViewHolder", String.valueOf(videoInfo.likecount));
        videoViewHolder.likeCount.setText(String.valueOf(videoInfo.likecount));
        Glide.with(videoViewHolder.videoView)
                .load(videoInfo.avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(videoViewHolder.avatar);
        Glide.with(videoViewHolder.videoView)
                .setDefaultRequestOptions(new RequestOptions().frame(3000000).centerCrop())
                .load(videoInfo.feedurl)
                .into(videoViewHolder.videoView.posterImageView);
        videoViewHolder.videoView.setUp(videoInfo.feedurl, videoInfo.description);
    }

    @Override
    public int getItemCount() {
        return videoInfoList == null ? 0 : videoInfoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nickname;
        public TextView description;
        public TextView likeCount;
        public TextView heart;
        public ImageView avatar;
        public MyVideoView videoView;
        public boolean like;

        public VideoViewHolder(@NonNull View itemView){
            super(itemView);
            like = false;
            videoView = itemView.findViewById(R.id.pro_video_view);
            nickname = itemView.findViewById(R.id.video_id);
            description = itemView.findViewById(R.id.video_description);
            avatar = itemView.findViewById(R.id.avatar_img);
            likeCount = itemView.findViewById(R.id.like_count);
            heart = itemView.findViewById(R.id.heart);

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (like){
                        like = false;
                        heart.setBackground(context.getResources().getDrawable(R.drawable.ic_white_heart));
                    }
                    else{
                        like = true;
                        heart.setBackground(context.getResources().getDrawable(R.drawable.ic_red_heart));
                    }
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
    }

    public void setVideoInfoList(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
