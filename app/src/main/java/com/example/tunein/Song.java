package com.example.tunein;

import com.google.gson.annotations.SerializedName;

public class Song {
    private String message;
    private long id;
    private String styleName;
    private String genreName;
    private int duration;
    private String title;
    private String artistName;
    private String playURL;

    public long getId() {
        return id;
    }

    public String getStyleName() {
        return styleName;
    }

    public String getGenreName() {
        return genreName;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    private int userId;

    public Song(long id, String styleName, String genreName, int duration, String title, String artistName, String playURL) {
        this.id = id;
        this.styleName = styleName;
        this.genreName = genreName;
        this.duration = duration;
        this.title = title;
        this.artistName = artistName;
        this.playURL = playURL;
    }

    public String getPlayURL() {
        return playURL;
    }

    @SerializedName("body")
    private String text;

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

}
