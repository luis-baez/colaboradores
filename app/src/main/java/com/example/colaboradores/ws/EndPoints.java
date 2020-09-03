package com.example.colaboradores.ws;

import com.example.colaboradores.ws.response.ColaboratorsResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface EndPoints {

    @GET("5u21281sca8gj94/getFile.json?dl=0")
    Call<ColaboratorsResponse> getColaborators();

    @GET()
    Call<ResponseBody> downloadJSON(@Url String url);


}
