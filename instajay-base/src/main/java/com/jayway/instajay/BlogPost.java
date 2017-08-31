package com.jayway.instajay;

public class BlogPost {

    private String published;
    private String title;
    private String text;
    private String author;
    private String authorPictureUrl;
    private String url;

    BlogPost(String title, String published, String text, String author, String authorPictureUrl, String url) {
        this.title = title;
        this.published = published;
        this.text = text;
        this.author = author;
        this.authorPictureUrl = authorPictureUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorPictureUrl() {
        return authorPictureUrl;
    }

    public String getPublished() {
        return published;
    }

    public String getUrl() {
        return url;
    }
}
