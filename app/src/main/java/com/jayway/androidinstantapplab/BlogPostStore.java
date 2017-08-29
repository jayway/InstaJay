package com.jayway.androidinstantapplab;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BlogPostStore {

    private List<BlogPost> blogPosts = new ArrayList<>();
    private static BlogPostStore instance;

    private BlogPostStore(Context context) {
        mockBlockPosts(context);

        Gson gson = new Gson();
    }

    public static BlogPostStore getInstance(Context context) {
        if (instance == null) {
            instance = new BlogPostStore(context);
        }
        return instance;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public BlogPost getBlogPost(String url) {
        if (blogPosts != null) {
            for (BlogPost blogPost : blogPosts) {
                if (blogPost.getUrl().equals(url)) {
                    return blogPost;
                }
            }
        }
        return null;
    }

    private void mockBlockPosts(Context context) {

        String json = readFromAsset(context);
        if (json != null) {
            Type listType = new TypeToken<List<BlogPost>>(){}.getType();
            blogPosts = new Gson().fromJson(json, listType);
        } else {
            blogPosts = new ArrayList<>();
        }
    }

    public String readFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("blog_posts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
