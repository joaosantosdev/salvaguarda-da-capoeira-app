package br.com.jovemdeveloper.salvaguardadacapoeira.controller;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import br.com.jovemdeveloper.salvaguardadacapoeira.model.Grupo;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.Login;

import java.util.ArrayList;
import java.util.List;

public class OperacaoGrupo {
    public FirebaseDatabase fb;
    public DatabaseReference dr;
    public FirebaseStorage fs;
    public StorageReference sr;
    public Context context;
    public Grupo grupo;
    public static ArrayList<String> nome_grupos = new ArrayList<String>();


    public OperacaoUsuario ou;
    GrupoAdapter adapter;

    Usuario usuario;
    List<Grupo>grupos = new ArrayList<>();
    public OperacaoGrupo(Context context){
        FirebaseDB.init();
        FirebaseSG.init();
        this.context = context;
        this.fb = FirebaseDB.getFb();
        this.dr = FirebaseDB.getDr("usuarios");
        this.fs = FirebaseSG.getFs();
        this.sr = FirebaseSG.getSr("grupos");

        ou = new OperacaoUsuario(context);
        ou.listar();




    }
    public void setCadastrar(Button button,boolean op){

        if(op == false){
            button.setText("Salvando...");
            button.setEnabled(op);
        }else {
            button.setText("Salvar");
            button.setEnabled(op);
        }

    }
    public void inserir(Grupo g, Uri imagem, String imgDefault, final Button btn) {

        setCadastrar(btn,false);
        if(imagem != null){

        }

        this.grupo = g;
        for(Usuario u:ou.usuarios){

            if(u.getId().equals(Login.usuario.getId())){
                usuario = u;
                break;
            }
        }
        usuario.setGrupo(grupo);

        if(imagem != null){
            sr.child(usuario.getId()).putFile(imagem).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sr.child(usuario.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            grupo.setUrlImagem(uri.toString());
                            dr.child(usuario.getId()).setValue(usuario);
                            Toast.makeText(context,"Salvo com sucesso",Toast.LENGTH_SHORT).show();
                            setCadastrar(btn,true);
                            ((Activity)context).finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Ocorreu um erro tente novamente",Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                }
            });
        }else{
            grupo.setUrlImagem(imgDefault);
            usuario.setGrupo(grupo);
            dr.child(usuario.getId()).setValue(usuario);

            Toast.makeText(context,"Salvo com sucesso",Toast.LENGTH_SHORT).show();
            setCadastrar(btn,true);
            ((Activity)context).finish();
        }


    }



    public void listar(final RecyclerView recyclerView){

               dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               grupos.clear();

                for(DataSnapshot dsLIsta:dataSnapshot.getChildren()){
                   Usuario u = dsLIsta.getValue(Usuario.class);

                    if(Login.usuario != null && u.getId().equals(Login.usuario.getId())){
                        Login.usuario = u;
                    }
                    if(u.getGrupo() != null){
                        grupos.add(u.getGrupo());
                    }

                }
                if(recyclerView != null){
                    adapter = new GrupoAdapter(context,grupos);
                    recyclerView.setAdapter(adapter);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    public void filter(String texto){
        List<Grupo> grupoFilter = new ArrayList<>();
        for(Grupo g:grupos){
            if(g.getNomeGrupo().toLowerCase().contains(texto) ||g.getNomeResponsavel().toLowerCase().contains(texto) || g.getMestreGrupo().toLowerCase().contains(texto) ||g.getEstado().toLowerCase().contains(texto) || g.getCidade().toLowerCase().contains(texto) || g.getBairro().toLowerCase().contains(texto) ||g.getRua().toLowerCase().contains(texto) ||g.getComplemento().toLowerCase().contains(texto) ){
                grupoFilter.add(g);
            }
        }
        adapter.filterList(grupoFilter);
    }





    public void exibirMensagem(String mensagem){
        Toast.makeText(context,mensagem,Toast.LENGTH_SHORT).show();
   }
}
