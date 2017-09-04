package com.jayway.instajay.blogpost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayway.instajay.BlogPost;
import com.jayway.instajay.BlogPostStore;
import com.jayway.instajay.NavigationUtil;
import com.squareup.picasso.Picasso;

public class BlogPostDetailActivity extends AppCompatActivity {

    private static final String TAG = BlogPostDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
//        String url = intent.getStringExtra("url");
//        final BlogPost blogPost = BlogPostStore.getInstance(this).getBlogPost(url);
//        if (blogPost == null) {
//            finish();
//            return;
//        }

        String url = intent.getData() == null ? "data null" : intent.getData().toString();
        Log.d(TAG, "uri from intent: " + url);

        final BlogPost blogPost = BlogPostStore.getInstance(this).getBlogPost(url);
        if (blogPost == null) {
            Log.d(TAG, "could not find blog post, finishing");
            finish();
            return;
        }

        setContentView(R.layout.activity_blog_post);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView published = findViewById(R.id.published);
        published.setText(String.format(getString(com.jayway.instajay.R.string.published_by), blogPost.getPublished(), blogPost.getAuthor()));

        TextView title = findViewById(R.id.title);
        title.setText(blogPost.getTitle());

        TextView text = findViewById(R.id.text);
        text.setText(Html.fromHtml(blogPost.getText()));

        ImageView authorPicture = findViewById(R.id.authorPicture);
        if (!TextUtils.isEmpty(blogPost.getAuthorPictureUrl())) {
            Picasso.with(this).load(blogPost.getAuthorPictureUrl()).error(com.jayway.instajay.R.drawable.unknown_author).into(authorPicture);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, blogPost.getUrl());
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, blogPost.getTitle());
                startActivity(Intent.createChooser(shareIntent, getResources().getText(com.jayway.instajay.R.string.send_to)));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isTaskRoot()) {
                    NavigationUtil.startAppLink(this, NavigationUtil.MAIN_ACTIVITY_URL);
                    finish();
                } else {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
