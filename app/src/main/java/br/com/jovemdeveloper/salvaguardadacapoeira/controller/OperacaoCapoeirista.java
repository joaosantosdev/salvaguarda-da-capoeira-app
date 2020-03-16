package br.com.jovemdeveloper.salvaguardadacapoeira.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Capoeirista;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.Login;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.Perfil;

import java.util.ArrayList;
import java.util.List;

public class OperacaoCapoeirista implements DialogInterface.OnClickListener {
    public FirebaseDatabase fb;
    public DatabaseReference dr;
    public FirebaseStorage fs;
    public StorageReference sr;
    public Context context;
    private int capoeiristaSelecionado= 0;
    public OperacaoUsuario ou;
    public Capoeirista capoeirista;
    CapoeiristaAdapter adapter;

    Usuario usuario;
    List<Capoeirista>capoeiristas = new ArrayList<>();
    public OperacaoCapoeirista(Context context){
        FirebaseDB.init();
        FirebaseSG.init();
        this.context = context;
        this.fb = FirebaseDB.getFb();
        this.dr = FirebaseDB.getDr("usuarios");
        this.fs = FirebaseSG.getFs();
        this.sr = FirebaseSG.getSr("capoeiristas");
        ou = new OperacaoUsuario(context);
        ou.listar();




    }
    public void inserir(Capoeirista c, Uri imagem, String imgDefault, final Button btn) {
     setCadastrar(btn,false);
        this.capoeirista = c;
            for(Usuario u:ou.usuarios){

                if(u.getId().equals(Login.usuario.getId())){
                    usuario = u;
                    break;
                }
            }
            usuario.setCapoeirista(capoeirista);

        if(imagem != null){
            sr.child(usuario.getId()).putFile(imagem).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sr.child(usuario.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            capoeirista.setUrlImagem(uri.toString());
                            setCadastrar(btn,true);
                            dr.child(usuario.getId()).setValue(usuario);

                            Toast.makeText(context,"Salvo com sucesso",Toast.LENGTH_SHORT).show();
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
            capoeirista.setUrlImagem(imgDefault);

            usuario.setCapoeirista(capoeirista);
            dr.child(usuario.getId()).setValue(usuario);
            setCadastrar(btn,true);
            Toast.makeText(context,"Salvo com sucesso",Toast.LENGTH_SHORT).show();
            ((Activity)context).finish();

        }

    }

    public void listar(final RecyclerView recyclerView){

        //criarDialog();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                capoeiristas.clear();
                for(DataSnapshot dsLIsta:dataSnapshot.getChildren()){
                    Usuario u = dsLIsta.getValue(Usuario.class);
                    if(Login.usuario != null && u.getId().equals(Login.usuario.getId())){
                        Login.usuario = u;
                    }
                    if(u.getCapoeirista() != null){
                    capoeiristas.add(u.getCapoeirista());
                    }
                }


                    adapter = new CapoeiristaAdapter(context,capoeiristas);
                    recyclerView.setAdapter(adapter);


                //searchView.setOnQueryTextListener(OperacaoCapoeirista.this);
                adapter.setOnItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        capoeiristaSelecionado = position;
                        criarDialog().show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void filter(String texto){
        List<Capoeirista> capoeiristasFilter = new ArrayList<>();
        for(Capoeirista c:capoeiristas){
            if(c.getNome().toLowerCase().contains(texto)|| c.getCpf().toLowerCase().contains(texto)|| c.getRg().toLowerCase().contains(texto)||c.getDataNascimento().toLowerCase().contains(texto)||c.getSexo().toLowerCase().contains(texto)||c.getTelefone().toLowerCase().contains(texto)||c.getEndereco().toLowerCase().contains(texto)||c.getApelido().toLowerCase().contains(texto)||c.getWhatsapp().toLowerCase().contains(texto)||c.getGraduacao().toLowerCase().contains(texto)||c.getAnoGraduacao().toLowerCase().contains(texto)||c.getGrupo().toLowerCase().contains(texto)||c.getEstilo().toLowerCase().contains(texto)||c.getNomeMestre().toLowerCase().contains(texto)||c.getApelidoMestre().toLowerCase().contains(texto)||c.getGraduacaoMestre().toLowerCase().contains(texto)){
                capoeiristasFilter.add(c);
            }
        }
        adapter.filterList(capoeiristasFilter);
    }


    private AlertDialog criarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        CharSequence[] ops = {"Ver Perfil"};
        builder.setTitle("Opções");
        builder.setItems(ops,OperacaoCapoeirista.this);
      return builder.create();
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


   @Override
   public void onClick(DialogInterface dialog, int item) {
        switch (item){
            case 0:

              Perfil.setCapoeirista(capoeiristas.get(capoeiristaSelecionado));
                Toast.makeText(context,capoeiristas.get(capoeiristaSelecionado).getNome(),Toast.LENGTH_SHORT).show();
               Intent it = new Intent(context,Perfil.class);
               context.startActivity(it);
               break;
           case 1:
                break;
        }
    }
    public void exibirMensagem(String mensagem){
        Toast.makeText(context,mensagem,Toast.LENGTH_SHORT).show();
    }
}
