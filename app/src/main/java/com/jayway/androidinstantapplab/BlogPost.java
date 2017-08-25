package com.jayway.androidinstantapplab;

class BlogPost {

    private String id;
    private String published;
    private String title;
    private String text;
    private String author;
    private String authorPictureUrl;
    private String shareUrl;

    BlogPost(String id, String title, String published, String text, String author, String authorPictureUrl, String shareUrl) {
        this.id = id;
        this.title = title;
        this.published = published;
        this.text = text;
        this.author = author;
        this.authorPictureUrl = authorPictureUrl;
        this.shareUrl = shareUrl;
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

    public String getId() {
        return id;
    }

    public String getShareUrl() {
        return shareUrl;
    }
}
