package com.example.tunein;

import com.google.gson.annotations.SerializedName;

public class Song {
//    private String message;
//    private long id;
//    private String styleName;
//    private String genreName;
//    private int duration;
//    private String title;
//    private String artistName;
//
//    public long getId() {
//        return id;
//    }
//
//    public String getStyleName() {
//        return styleName;
//    }
//
//    public String getGenreName() {
//        return genreName;
//    }
//
//    public int getDuration() {
//        return duration;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getArtistName() {
//        return artistName;
//    }
    private String message;

    private int userId;

    private int id;

    private String title;

    @SerializedName("body")
    private String text;

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

}
