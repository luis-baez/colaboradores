package com.example.colaboradores.colaborators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.colaboradores.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddColaborator extends AppCompatActivity implements AddColaboratorCallback{

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etEmail;

    private AddColaboratorPresenter addColaboratorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_colaborator);
        ButterKnife.bind(this);

        addColaboratorPresenter = new AddColaboratorPresenter(this);
    }

    @OnClick(R.id.fab_save)
    void addColaboratorClick(){
        if (!etEmail.getText().toString().isEmpty()  && !etName.getText().toString().isEmpty() ){
            addColaboratorPresenter.saveColaborator(etName.getText().toString(), etEmail.getText().toString());
        }else {
            Toast.makeText(this,"Faltan campos por llenar",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessAdd() {
        Toast.makeText(this,"Registro guardado de manera exitosa",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onErrorAdd() {
        Toast.makeText(this,"Error al agregar",Toast.LENGTH_LONG).show();
    }
}
