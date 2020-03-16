package br.com.jovemdeveloper.salvaguardadacapoeira.controller;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import android.net.Uri;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import br.com.jovemdeveloper.salvaguardadacapoeira.model.Evento;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.Login;

import java.util.ArrayList;
import java.util.List;


public class OperacaoEvento {

    private DatabaseReference dr;
    private StorageReference sr;
    private String key;
    private Context context;
    private List<Evento> eventos = new ArrayList<Evento>();
    private OperacaoUsuario ou;
    private Usuario usuario;
    private Evento evento;
    private AdapterEvento adpEventos;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean inserir;
    long timeStamp;
    long timeStampRemove;
    public OperacaoEvento(Context context, SharedPreferences preferences, SharedPreferences.Editor editor){
        FirebaseDB.init();
        FirebaseSG.init();
        inserir = true;
        dr = FirebaseDB.getDr("usuarios");
        sr = FirebaseSG.getSr("eventos");
        this.context = context;
        ou = new OperacaoUsuario(context);
        ou.listar();
        this.preferences = preferences;
        this.editor = editor;
    }


    public void upload(Uri imageUri, final String descricao, final String nome, final String email , final Button btn){
        this.key = Login.usuario.getId();
        for(Usuario u:ou.usuarios){

            if(u.getId().equals(key)){
                usuario = u;
                break;
            }
        }





          timeStamp = System.currentTimeMillis();
          timeStampRemove = timeStamp+86400000;

        sr.child(String.valueOf(timeStamp)).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sr.child(String.valueOf(timeStamp)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        evento = new Evento();
                        evento.setKey(String.valueOf(timeStamp));
                        evento.setDescricao(descricao);
                        evento.setTimeStamp(String.valueOf(timeStamp));
                        evento.setUrl(uri.toString());
                        evento.setTimeStampRemove(String.valueOf(timeStampRemove));
                        evento.setNomeDoProprietario(nome);
                        evento.setEmailDoProprietario(email);
                        usuario.getEventos().add(evento);
                        dr.child(key).setValue(usuario);
                        btn.setEnabled(true);
                        btn.setText("Adicionar");
                        exibirMensagem("Evento adicionado com sucesso");
                        ((Activity)context).finish();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot ts) {
                double progresso = (100.0 * ts.getBytesTransferred()/ts.getTotalByteCount());
               // pbProgresso.setProgress((int)progresso);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagem("Ocorreu um erro tente novemente");
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                exibirMensagem("Upload cancelado");
            }
        });
    }

    public void exibirMensagem(String mensagem){
        Toast.makeText(context,mensagem,Toast.LENGTH_SHORT).show();
    }

    /*

    public void remover(final Imagem i){
        exibirMensagem("remover");
        sr.getStorage().getReferenceFromUrl(i.getUrl()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dr.child(i.getKey()).removeValue();
                exibirMensagem("Removido com sucesso");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagem(e.toString());
            }
        });
    }
    public void baixar(Imagem i){

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            dFile = new DownloadFile(context);
            if(i.getDescricao() !=  ""){
                dFile.download(i.getUrl(),"Imagens",i.getDescricao()+"."+i.getExtensao());
            }else{
                dFile.download(i.getUrl(),"Imagens","amorsecreto"+"."+i.getExtensao());
            }


        }else{
            Toast.makeText(context,"Permissão negada - Configure seu dispositivo",Toast.LENGTH_SHORT).show();
        }


    }
*/
    public void listar(final RecyclerView lstEventos){
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(inserir){
                    try {


                        eventos.clear();
                        for (DataSnapshot dsLIsta : dataSnapshot.getChildren()) {

                            Usuario u = dsLIsta.getValue(Usuario.class);
                            if(Login.usuario != null && u.getId().equals(Login.usuario.getId())){
                                Login.usuario = u;
                            }


                            for(Evento e:u.getEventos()){
                                eventos.add(e);
                            }


                        }
                        adpEventos = new AdapterEvento(context, eventos);
                        lstEventos.setAdapter(adpEventos);

                    }catch (Exception e){
                        Log.i("MRSCRIPT",e.toString());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
