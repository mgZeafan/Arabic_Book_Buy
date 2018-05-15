package com.app.mohamedgomaa.arabic_books;

import java.io.Serializable;

/**
 * Created by Mohamed Gooma on 9/18/2017.
 */

 class item implements Serializable{
    String book_id,title_ar,title_en,details_en,details_ar,author_en,author_ar,pth_photo,pth_review,pth_cd,pth_book;
    double price;

    public item(String book_id, String title_ar, String title_en, String details_en, String details_ar,String author_ar,String author_en, String pth_photo, String pth_review, String pth_cd, String pth_book, double price) {
        this.title_ar = title_ar;
        this.title_en = title_en;
        this.details_en = details_en;
        this.details_ar = details_ar;
        this.author_ar=author_ar;
        this.author_en=author_en;
        this.pth_photo = pth_photo;
        this.pth_review = pth_review;
        this.pth_cd = pth_cd;
        this.pth_book = pth_book;
        this.price = price;
    }
    public item() {
        this.book_id ="";
        this.title_ar = "";
        this.title_en = "";
        this.details_en = "";
        this.details_ar = "";
        this.pth_photo = "";
        this.pth_review = "";
        this.pth_cd = "";
        this.pth_book = "";
        this.price = 0;
    }

}
