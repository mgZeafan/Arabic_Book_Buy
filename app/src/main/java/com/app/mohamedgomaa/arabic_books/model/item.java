package com.app.mohamedgomaa.arabic_books.model;

import android.support.annotation.Keep;

import java.io.Serializable;

/**
 * Created by Mohamed Gooma on 9/18/2017.
 */
@Keep
 public class item implements Serializable{
  public   String book_id,title_ar,title_en,details_en,details_ar,author_en,author_ar,pth_photo,pth_review,pth_cd,pth_book;
 public double price;

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

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getTitle_ar() {
        return title_ar;
    }

    public void setTitle_ar(String title_ar) {
        this.title_ar = title_ar;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getDetails_en() {
        return details_en;
    }

    public void setDetails_en(String details_en) {
        this.details_en = details_en;
    }

    public String getDetails_ar() {
        return details_ar;
    }

    public void setDetails_ar(String details_ar) {
        this.details_ar = details_ar;
    }

    public String getAuthor_en() {
        return author_en;
    }

    public void setAuthor_en(String author_en) {
        this.author_en = author_en;
    }

    public String getAuthor_ar() {
        return author_ar;
    }

    public void setAuthor_ar(String author_ar) {
        this.author_ar = author_ar;
    }

    public String getPth_photo() {
        return pth_photo;
    }

    public void setPth_photo(String pth_photo) {
        this.pth_photo = pth_photo;
    }

    public String getPth_review() {
        return pth_review;
    }

    public void setPth_review(String pth_review) {
        this.pth_review = pth_review;
    }

    public String getPth_cd() {
        return pth_cd;
    }

    public void setPth_cd(String pth_cd) {
        this.pth_cd = pth_cd;
    }

    public String getPth_book() {
        return pth_book;
    }

    public void setPth_book(String pth_book) {
        this.pth_book = pth_book;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
