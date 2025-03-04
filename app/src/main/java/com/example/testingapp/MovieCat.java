package com.example.testingapp;

public class MovieCat {
    private String title;
    private String description;
    private int thumbnail;
    private String studio;
    private String rating;
    private String streamingLink;
    private String coverPhoto;


    public MovieCat(String title, int thumbnail, String coverPhoto) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.coverPhoto = coverPhoto;
    }

    public MovieCat(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

//    public MovieCat(String title, String description, int thumbnail, String studio, String rating, String streamingLink) {
//        this.title = title;
//        this.description = description;
//        this.thumbnail = thumbnail;
//        this.coverPhoto = thumbnail;
//        this.studio = studio;
//        this.rating = rating;
//        this.streamingLink = streamingLink;
//    }


    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getStudio() {
        return studio;
    }

    public String getRating() {
        return rating;
    }

    public String getStreamingLink() {
        return streamingLink;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setStreamingLink(String streamingLink) {
        this.streamingLink = streamingLink;
    }
}
