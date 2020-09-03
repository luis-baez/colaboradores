package com.example.colaboradores.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Colaborator implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("mail")
    @Expose
    private String mail;

    public Colaborator(Parcel in) {
        id = in.readString();
        name = in.readString();
        mail = in.readString();
    }

    public static final Creator<Colaborator> CREATOR = new Creator<Colaborator>() {
        @Override
        public Colaborator createFromParcel(Parcel in) {
            return new Colaborator(in);
        }

        @Override
        public Colaborator[] newArray(int size) {
            return new Colaborator[size];
        }
    };

    public Colaborator() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(mail);
    }
}