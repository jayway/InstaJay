package com.jayway.instajay;

class Event {

    private String date;
    private String time;
    private String title;
    private String text;
    private String location;
    private String imageUrl;
    private String url;

    Event(String title, String date, String time, String text, String location, String imageUrl, String url) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.text = text;
        this.location = location;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }
}
