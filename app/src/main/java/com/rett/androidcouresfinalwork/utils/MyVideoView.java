package com.rett.androidcouresfinalwork.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.rett.androidcouresfinalwork.R;


public class MyVideoView extends VideoView {


    public ImageView previewImage;
    private Context v_context;

    public MyVideoView(Context context) {
        super(context);
        v_context = context;
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        v_context = context;
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        v_context = context;
        init();
    }

    private void init(){
        previewImage = findViewById(R.id.preview_image);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                previewImage.setVisibility(View.GONE);
                Toast.makeText(v_context, "Loading", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
