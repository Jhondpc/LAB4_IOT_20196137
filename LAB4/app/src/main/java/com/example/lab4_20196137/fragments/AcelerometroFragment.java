package com.example.lab4_20196137.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4_20196137.ContactoAcelerometroAdapter;
import com.example.lab4_20196137.ContactoMagnetometroAdapter;
import com.example.lab4_20196137.R;
import com.example.lab4_20196137.databinding.FragmentAcelerometroBinding;
import com.example.lab4_20196137.dto.Usuario;
import com.example.lab4_20196137.dto.UsuarioResponse;
import com.example.lab4_20196137.services.TypicodeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AcelerometroFragment extends Fragment {

    FragmentAcelerometroBinding binding;
    TypicodeService typicodeService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAcelerometroBinding.inflate(inflater,container,false);
        createRetrofitService();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        cargarListaWebService();
    }

    public void createRetrofitService(){
        typicodeService = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeService.class);
    }
    public void cargarListaWebService() {
        typicodeService.getUser().enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()) {
                    UsuarioResponse body = response.body();
                    List<Usuario> usuarioList = body.getUsuarios();

                    ContactoAcelerometroAdapter contactoAdapter = new ContactoAcelerometroAdapter();
                    contactoAdapter.setUsuarioList(usuarioList);
                    contactoAdapter.setContext(getActivity());

                    binding.recycleViewContacsAcel.setAdapter(contactoAdapter);
                    binding.recycleViewContacsAcel.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    public void agregarContactoAcelerometro() {
    }
}