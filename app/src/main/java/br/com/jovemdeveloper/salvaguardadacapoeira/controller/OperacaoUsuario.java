package br.com.jovemdeveloper.salvaguardadacapoeira.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class OperacaoUsuario {

        public FirebaseDatabase fb;
        public DatabaseReference dr;
        public FirebaseStorage fs;
        public StorageReference sr;
        public Context context;
        public static List<Usuario>usuarios = new ArrayList<>();
        public OperacaoUsuario(Context context){
            FirebaseDB.init();
            FirebaseSG.init();
            this.context = context;
            this.fb = FirebaseDB.getFb();
            this.dr = FirebaseDB.getDr("usuarios");
            this.fs = FirebaseSG.getFs();
            this.sr = FirebaseSG.getSr("usuarios");

        }
        public void inserir(Usuario usuario) {

            dr.child(usuario.getId()).setValue(usuario);
        }
        public  void listar(){
            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dsLista:dataSnapshot.getChildren()){
                        Usuario u = dsLista.getValue(Usuario.class);
                        usuarios.add(u);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }









}
