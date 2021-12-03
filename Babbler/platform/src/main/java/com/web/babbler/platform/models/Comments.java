package com.web.babbler.platform.models;

public class Comments
{
    private String id;
    private String sender;
    private String caption;
    private String date;
    private String timeStamp;

    public Comments(String id, String sender, String caption, String date, String timeStamp) {
        this.id = id;
        this.sender = sender;
        this.caption = caption;
        this.date = date;
        this.timeStamp = timeStamp;
    }
    public Comments(){

    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
