package com.web.blabber.platform.models;

public class Thread
{
    private String title;
    private String sender;
    private String caption;
    private String date;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;

    public Thread(String title, String sender,
                  String caption, String date,
                  String image1, String image2, String image3,
                  String image4, String image5) {
        this.title = title;
        this.sender = sender;
        this.caption = caption;
        this.date = date;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
    }

    public Thread()
    {

    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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

    public String getImage1() {
        return image1;
    }
    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }
    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }
    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }
    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }
    public void setImage5(String image5) {
        this.image5 = image5;
    }
}
