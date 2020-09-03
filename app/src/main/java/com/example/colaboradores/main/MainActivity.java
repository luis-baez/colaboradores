package com.example.colaboradores.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.colaboradores.R;
import com.example.colaboradores.colaborators.AddColaborator;
import com.example.colaboradores.colaborators.Colaborators;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainCallback {

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mainPresenter.dbExist();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                mainPresenter.dbExist();
                break;
            default:
                Toast.makeText(this,"No se tienen permisos de escritura",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @OnClick(R.id.btn_view_colaborators)
    public void viewColaboratorsClick(){
        startActivity(new Intent(this, Colaborators.class));
    }

    @OnClick(R.id.btn_add_colaborator)
    public void addColaboratorClick(){
        startActivity(new Intent(this, AddColaborator.class));
    }

    @Override
    public void onDbExist(boolean exist) {
        if (!exist)
            mainPresenter.getColaborators();
    }

    @Override
    public void onErrorGetColaboratorst() {
        Toast.makeText(this,"Error al descargar",Toast.LENGTH_LONG).show();
    }
}
