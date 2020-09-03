package com.example.colaboradores.colaborators;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.colaboradores.model.Colaborator;
import com.example.colaboradores.model.ColaboratorsData;
import com.example.colaboradores.model.Location;
import com.example.colaboradores.utils.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddColaboratorPresenter {

    private AddColaboratorCallback addColaboratorCallback;

    public AddColaboratorPresenter(AddColaboratorCallback addColaboratorCallback) {
        this.addColaboratorCallback = addColaboratorCallback;
    }

    public void saveColaborator(String name, String email){

        Colaborator colaborator = new Colaborator();
        int idColaborator = (int)(Math.random()*1000+1);

        colaborator.setName(name);
        colaborator.setMail(email);
        colaborator.setLocation(generateLocation());
        colaborator.setId(String.valueOf(idColaborator));
        String data = getData();
        Gson gson = new Gson();
        ColaboratorsData colaboratorsData = gson.fromJson(data, ColaboratorsData.class);
        if (colaboratorsData != null && colaboratorsData.getData() != null){
            if (colaboratorsData.getData().getColaborators() != null){
                colaboratorsData.getData().getColaborators().add(colaborator);
            }else{
                List<Colaborator> colaboratorList = new ArrayList<>();
                colaboratorList.add(colaborator);
                colaboratorsData.getData().setColaborators(colaboratorList);
            }

            saveData(gson.toJson(colaboratorsData));
        }else {
            addColaboratorCallback.onErrorAdd();
        }
    }

    private void saveData( String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(Constants.FILE_PATH + Constants.FILE_NAME_COMPLETE);
            file.write(mJsonResponse);
            file.flush();
            file.close();
            addColaboratorCallback.onSuccessAdd();
        } catch (IOException e) {
            e.printStackTrace();
            addColaboratorCallback.onErrorAdd();
        }
    }

    private String getData() {
        try { File f = new File(Constants.FILE_PATH + Constants.FILE_NAME_COMPLETE);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            return null; }
    }

    private Location generateLocation(){
        Location location = new Location();
        double minLat = 19.100;
        double maxLat = 19.900;
        double latitude = minLat + (double)(Math.random() * ((maxLat - minLat) + 1));
        double minLon = -99.00;
        double maxLon = -99.900;
        double longitude = minLon + (double)(Math.random() * ((maxLon - minLon) + 1));
        DecimalFormat df = new DecimalFormat("#.#####");
        location.setLat(df.format(latitude));
        location.setLog(df.format(longitude));
        return location;
    }
}
