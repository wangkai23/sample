package com.example.wangkai.dragonehelper;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final LinearLayout catWork = (LinearLayout) findViewById(R.id.cat_work);
        catWork.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {


                int action = dragEvent.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        catWork.setBackgroundColor(Color.YELLOW);
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        catWork.setBackgroundColor(Color.RED);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        catWork.setBackgroundColor(Color.YELLOW);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                        String catText = dragEvent.getClipData().getItemAt(0).getText().toString();
                        Bitmap catBitmap = stringToBitmap(catText);

                        final ImageView catView = new ImageView(getApplicationContext());
                        catView.setImageBitmap(catBitmap);
                        catWork.addView(catView);

                        setCatListener(catView, (LinearLayout) view, catBitmap);

                        Toast.makeText(getApplicationContext(), "ACTION_DROP_helper", Toast.LENGTH_SHORT).show();
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        catWork.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void setCatListener (final ImageView catView, final LinearLayout catWork, final Bitmap catBitmap) {
        catView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipData.Item item = new ClipData.Item(bitmapToString(catBitmap));
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData("", mimeTypes, item);

                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(catView);

                view.startDragAndDrop(dragData, myShadow, view, View.DRAG_FLAG_GLOBAL);
                catWork.removeView(catView);
                return true;
            }
        });
    }

    public final static String bitmapToString(Bitmap in){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return Base64.encodeToString(bytes.toByteArray(),Base64.DEFAULT);
    }
    public final static Bitmap stringToBitmap(String in){
        byte[] bytes = Base64.decode(in, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
