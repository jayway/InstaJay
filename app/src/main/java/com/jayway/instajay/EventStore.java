package com.jayway.instajay;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventStore {

    private List<Event> events = new ArrayList<>();
    private static EventStore instance;

    private EventStore(Context context) {
        mockBlockPosts(context);
    }

    public static EventStore getInstance(Context context) {
        if (instance == null) {
            instance = new EventStore(context);
        }
        return instance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Event getEvent(String url) {
        if (events != null) {
            for (Event event : events) {
                if (event.getUrl().equals(url)) {
                    return event;
                }
            }
        }
        return null;
    }

    private void mockBlockPosts(Context context) {

        String json = readFromAsset(context);
        if (json != null) {
            Type listType = new TypeToken<List<Event>>(){}.getType();
            events = new Gson().fromJson(json, listType);
        } else {
            events = new ArrayList<>();
        }
    }

    public String readFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("events.json");
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
