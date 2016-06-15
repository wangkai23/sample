package com.android.multiwindowplayground.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;
import com.android.multiwindowplayground.R;


public class SecondActivityDragInOneApp extends AppCompatActivity {

    private static final String TAG = "SecondActivityDragInOneApp";
    private TextView dropedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_in_one_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "I'm Second Activity！", Snackbar.LENGTH_LONG).show();
            }
        });

        dropedText = (TextView) findViewById(R.id.text_drop);
        dropedText.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(TAG, "Action is DragEvent.ACTION_DRAG_STARTED");
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(TAG, "Action is DragEvent.ACTION_DRAG_ENTERED");
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(TAG, "Action is DragEvent.ACTION_DRAG_EXITED");
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(TAG, "Action is DragEvent.ACTION_DRAG_ENDED");
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d(TAG, "ACTION_DROP event");
                        dropedText.setText(dragEvent.getClipData().getItemAt(0).getText());
                        break;

                    default:
                        break;
                }

                return true;
            }
        });

        dropedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropedText.setText("I'm reverted");
            }
        });
    }


    @Override
    public DragAndDropPermissions requestDragAndDropPermissions(DragEvent event) {
        Log.d(TAG, "requestDropPermissions");
        return super.requestDragAndDropPermissions(event);
    }

    @Override
    public void onMultiWindowModeChanged(boolean inMultiWindow) {
        Log.i(TAG, "inMultiWindow = " + inMultiWindow);
        super.onMultiWindowModeChanged(inMultiWindow);
    }
}
