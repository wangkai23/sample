package com.android.multiwindowplayground.activities;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.android.multiwindowplayground.R;


public class MainActivityDragInOneApp extends AppCompatActivity {

    private ImageView imageView;
    private static final String TAG = "MainActivityDragInOneApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_in_one_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.launch_second_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityDragInOneApp.this, SecondActivityDragInOneApp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imageView = (ImageView) findViewById(R.id.img);
        /** 拖拽的发送方Activity和ImageView */
        imageView.setTag("I'm a ImageView from MainActivityDragInOneApp");
        imageView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    /** 构造一个ClipData，将需要传递的数据放在里面 */
                    ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    ClipData dragData = new ClipData(view.getTag().toString(), mimeTypes, item);
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(imageView);
                    /** startDragAndDrop是Android N SDK中的新方法，替代了以前的startDrag，flag需要设置为DRAG_FLAG_GLOBAL */
                    view.startDragAndDrop(dragData, shadow, null, View.DRAG_FLAG_GLOBAL);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void onMultiWindowModeChanged(boolean inMultiWindow) {
        Log.i(TAG, "inMultiWindow = " + inMultiWindow);
        super.onMultiWindowModeChanged(inMultiWindow);
    }

}
