package com.example.colaboradores.colaborators;

import android.content.Context;
import android.util.Log;

import com.example.colaboradores.model.ColaboratorsData;
import com.example.colaboradores.utils.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ColaboratorsPresenter {
    private Context context;
    private ColaboratorsCallback colaboratorsCallback;

    public ColaboratorsPresenter(Context context, ColaboratorsCallback colaboratorsCallback) {
        this.context = context;
        this.colaboratorsCallback = colaboratorsCallback;
    }

    public void getColaborators(){
        try {
            String data = getData();
            Gson gson = new Gson();
            ColaboratorsData colaboratorsData = gson.fromJson(data, ColaboratorsData.class);

            colaboratorsCallback.onLoadedInformation(colaboratorsData.getData().getEmployees());

        }catch (Exception e){
            e.printStackTrace();
            colaboratorsCallback.onLoadedInformation(new ArrayList<>());
        }

    }

    private static String getData() {
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
}
