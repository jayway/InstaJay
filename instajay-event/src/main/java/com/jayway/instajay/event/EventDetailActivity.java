package com.jayway.instajay.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayway.instajay.BlogPost;
import com.jayway.instajay.BlogPostStore;
import com.jayway.instajay.Event;
import com.jayway.instajay.EventStore;
import com.squareup.picasso.Picasso;

public class EventDetailActivity extends AppCompatActivity {

    private static final String TAG = EventDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        String url = intent.getData() == null ? "data null" : intent.getData().toString();
        Log.d(TAG, "uri from intent: " + url);

        final Event event = EventStore.getInstance(this).getEvent(url);
        if (event == null) {
            Log.d(TAG, "could not find event, finishing");
            finish();
            return;
        }

        setContentView(R.layout.activity_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title = findViewById(R.id.title);
        title.setText(event.getTitle());

        TextView dateAndTime = findViewById(R.id.date_time);
        dateAndTime.setText(event.getDate() + " " + event.getTime());

        TextView location = findViewById(R.id.location);
        location.setText(event.getLocation());

        TextView text = findViewById(R.id.text);
        text.setText(Html.fromHtml(event.getText()));

        final ImageView image = findViewById(R.id.image);
        if (!TextUtils.isEmpty(event.getImageUrl())) {
            image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Picasso.with(EventDetailActivity.this).load(event.getImageUrl()).error(com.jayway.instajay.R.drawable.unknown_author).resize(image.getWidth(), 0).into(image);
                }
            });
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, event.getUrl());
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, event.getTitle());
                startActivity(Intent.createChooser(shareIntent, getResources().getText(com.jayway.instajay.R.string.send_to)));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}
