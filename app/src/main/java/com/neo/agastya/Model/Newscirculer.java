package com.neo.agastya.Model;

public class Newscirculer{

    String date;
    String title;
    String message;
    String link;
    String image;
    int imageeye;



    public Newscirculer(String date, String title, String message, String link, String image, int imageeye) {
        this.date = date;
        this.title = title;
        this.message = message;
        this.link = link;
        this.image = image;
        this.imageeye = imageeye;
    }

    public int getImageeye() {
        return imageeye;
    }

    public void setImageeye(int imageeye) {
        this.imageeye = imageeye;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
