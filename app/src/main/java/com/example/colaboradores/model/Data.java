package com.example.colaboradores.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("employees")
    @Expose
    private List<Colaborator> colaborators = null;

    public List<Colaborator> getEmployees() {
        return colaborators;
    }

    public void setEmployees(List<Colaborator> colaborators) {
        this.colaborators = colaborators;
    }
}