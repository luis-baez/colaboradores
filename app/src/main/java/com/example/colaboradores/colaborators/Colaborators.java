package com.example.colaboradores.colaborators;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.colaboradores.R;
import com.example.colaboradores.model.Colaborator;
import com.example.colaboradores.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Colaborators extends AppCompatActivity implements ColaboratorsCallback {

    private ColaboratorsAdapter colaboratorsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ColaboratorsPresenter colaboratorsPresenter;


    @BindView(R.id.recycler_colaborators)
    RecyclerView recyclerView;

    List<Colaborator> colaborators;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colaborators);
        ButterKnife.bind(this);


        colaboratorsPresenter = new ColaboratorsPresenter(this, this);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        colaboratorsPresenter.getColaborators();


    }


    @Override
    public void onLoadedInformation(List<Colaborator> data) {
        if (data != null && data.size()>0){
            colaborators = data;
            colaboratorsAdapter = new ColaboratorsAdapter(this,this, data);
            recyclerView.setAdapter(colaboratorsAdapter);
            colaboratorsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(Colaborator colaborator) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Gson gson = new Gson();
            Intent intent = new Intent(this, MapsColaborators.class);
            intent.putExtra("isAll",false);
            intent.putExtra("data",gson.toJson(colaborator));
            startActivity(intent);
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }
    }

    @OnClick(R.id.fab_map)
    void onMapClick(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Gson gson = new Gson();
            Intent intent = new Intent(this, MapsColaborators.class);
            intent.putExtra("isAll",true);
            intent.putExtra("data",gson.toJson(colaborators));
            startActivity(intent);
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }


    }
}
