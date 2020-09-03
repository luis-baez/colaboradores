package com.example.colaboradores.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Parcelable {

    @SerializedName("employees")
    @Expose
    private List<Colaborator> colaborators = null;


    public Data() {

    }

    protected Data(Parcel in) {
        colaborators = in.createTypedArrayList(Colaborator.CREATOR);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public List<Colaborator> getColaborators() {
        return colaborators;
    }

    public void setColaborators(List<Colaborator> colaborators) {
        this.colaborators = colaborators;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(colaborators);
    }
}