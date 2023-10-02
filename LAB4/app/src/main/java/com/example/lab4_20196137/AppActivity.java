package com.example.lab4_20196137;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4_20196137.databinding.ActivityAppBinding;
import com.example.lab4_20196137.dto.Usuario;
import com.example.lab4_20196137.dto.UsuarioResponse;
import com.example.lab4_20196137.fragments.AcelerometroFragment;
import com.example.lab4_20196137.fragments.MagnetometroFragment;
import com.example.lab4_20196137.fragments.MagnetometroFragmentDirections;
import com.example.lab4_20196137.services.TypicodeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {

    ActivityAppBinding binding;
    private boolean isMagnometroFragment = true;

    private Fragment fragmentActivo;
    private TypicodeService typicodeService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
/*
        binding.btnAnadirContacto.setOnClickListener(view -> {
            if (fragmentActivo != null) {
                if (fragmentActivo instanceof MagnetometroFragment) {
                    createRetrofitService();
                    cargarListaWebService();
                } else if (fragmentActivo instanceof AcelerometroFragment) {
                    ((AcelerometroFragment) fragmentActivo).agregarContactoAcelerometro();
                }
            }
        });

 */


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController navController = navHostFragment.getNavController();

        //Gestión del cambio de texto en el botón
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.acelerometroFragment) {
                //Cambia el texto del botón cuando se va al fragmento Acelerómetro
                actualizarTexto("Ir a Magnetómetro");
                fragmentActivo = new AcelerometroFragment(); // Actualiza el fragmento activo
            } else if (destination.getId() == R.id.magnetometroFragment) {
                //Cambia el texto del botón cuando se va al fragmento Magnetómetro
                actualizarTexto("Ir a Acelerómetro");
                fragmentActivo = new MagnetometroFragment();
            }
        });

        binding.btnCambiarSensor.setOnClickListener(view -> {
            if(isMagnometroFragment){
                navController.navigate(R.id.action_magnetometroFragment_to_acelerometroFragment);
            }else{
                navController.navigate(R.id.action_acelerometroFragment_to_magnetometroFragment);
            }
            isMagnometroFragment = !isMagnometroFragment;

        });
    }

    private void actualizarTexto(String newText) {
        //Actualizar el texto del botón
        TextView btnCambiarSensor = findViewById(R.id.btnCambiarSensor);
        btnCambiarSensor.setText(newText);
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
                    UsuarioResponse userResponse = response.body();
                    if(userResponse != null && !userResponse.getUsuarios().isEmpty()){
                        Usuario nuevoContacto = userResponse.getUsuarios().get(0);
                        if (fragmentActivo instanceof MagnetometroFragment) {
                            ((MagnetometroFragment) fragmentActivo).agregarContactoMagnetometro(nuevoContacto);
                        }
                    }
/*
                    ContactoMagnetometroAdapter contactoAdapter = new ContactoMagnetometroAdapter();
                    contactoAdapter.setUsuarioList(usuarioList);
                    contactoAdapter.setContext(AppActivity.this);

                    binding.recycleViewContacsMag.setAdapter(contactoAdapter);
                    binding.recycleViewContacsMag.setLayoutManager(new LinearLayoutManager(getActivity()));

 */
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }
}