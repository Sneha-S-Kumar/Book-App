package com.example.bookapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book extends User implements Parcelable {
    private String id;
    private String author;

    private String name;
    private String date;
    private Uri imagepath;
    private String addeduser;

    public Book(String id, String name, String author, String date, Uri imagepath, String addeduser) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.name = name;
        this.imagepath = imagepath;
        this.addeduser = addeduser;
    }

    protected Book(Parcel in) {
        id = in.readString();
        name = in.readString();
        author = in.readString();
        date = in.readString();
        imagepath = Uri.parse(in.readString());
        addeduser = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        private Parcel source;

        @Override
        public Book createFromParcel(Parcel in) {

            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[0];
        }
    };


    // Getters and setters (if needed)
    public String getId() {
        return id;
    }

    public String getUser() {
        return addeduser;
    }

    public void setUser(String addeduser) {
        this.addeduser = addeduser;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Uri getImagepath() {
        return imagepath;
    }

    public void setImagepath(Uri imagepath) {
        this.imagepath = imagepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(String.valueOf(imagepath));
        dest.writeString(addeduser);
    }
}
