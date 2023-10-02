package com.example.lab4_20196137.services;


import com.example.lab4_20196137.dto.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TypicodeService {

    @GET("/api")
    Call<UsuarioResponse> getUser();
}
