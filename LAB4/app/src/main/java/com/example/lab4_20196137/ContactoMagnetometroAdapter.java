package com.example.lab4_20196137;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20196137.dto.Usuario;

import java.util.List;

public class ContactoMagnetometroAdapter extends RecyclerView.Adapter<ContactoMagnetometroAdapter.ContactoViewHolder>{

    private List<Usuario> usuarioList;
    private Context context;

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.irv_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position){
        Usuario usuario = usuarioList.get(position);
        holder.usuario = usuario;

        TextView nombreContacto = holder.itemView.findViewById(R.id.textViewNombreContacto);
        TextView generoContacto = holder.itemView.findViewById(R.id.generoContacto);
        TextView ciudadContacto = holder.itemView.findViewById(R.id.ciudadContacto);
        TextView paisContacto = holder.itemView.findViewById(R.id.paisContacto);
        TextView emailContacto = holder.itemView.findViewById(R.id.emailContacto);
        TextView telefonoContacto = holder.itemView.findViewById(R.id.telefonoContacto);
        ImageView fotoContacto = holder.itemView.findViewById(R.id.fotoContacto);

        nombreContacto.setText(usuario.getNombre().getTitulo()+" "+usuario.getNombre().getApellido()+" "+usuario.getNombre().getNombre());
        generoContacto.setText(usuario.getGenero());
        ciudadContacto.setText(usuario.getDireccion().getCiudad());
        paisContacto.setText(usuario.getDireccion().getPais());
        emailContacto.setText(usuario.getEmail());
        telefonoContacto.setText(usuario.getTelefono());
        Glide.with(context)
                .load(usuario.getImagen().getGrande())
                .into(fotoContacto);
    }

    @Override
    public int getItemCount(){
        return usuarioList.size();
    }


    public class ContactoViewHolder extends RecyclerView.ViewHolder{
        Usuario usuario;
        public ContactoViewHolder(@NonNull View itemView){
            super(itemView);

        }
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
