package com.example.colaboradores.colaborators;

import com.example.colaboradores.model.Colaborator;

import java.util.List;

public interface ColaboratorsCallback {
    void onLoadedInformation(List<Colaborator> data);
    void onItemClick(Colaborator colaborator);
}
