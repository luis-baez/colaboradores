package com.example.colaboradores.colaborators;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.colaboradores.R;
import com.example.colaboradores.model.Colaborator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Colaborators extends AppCompatActivity implements ColaboratorsCallback {

    private ColaboratorsAdapter colaboratorsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ColaboratorsPresenter colaboratorsPresenter;


    @BindView(R.id.recycler_colaborators)
    RecyclerView recyclerView;

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
            colaboratorsAdapter = new ColaboratorsAdapter(this,this, data);
            recyclerView.setAdapter(colaboratorsAdapter);
            colaboratorsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(Colaborator colaborator) {
        Toast.makeText(this,"item Click: "+colaborator.getName(),Toast.LENGTH_LONG).show();
    }
}
