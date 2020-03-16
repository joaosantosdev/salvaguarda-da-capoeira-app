package br.com.jovemdeveloper.salvaguardadacapoeira.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.com.jovemdeveloper.salvaguardadacapoeira.model.Roda;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.Login;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.Mapa;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.VisualizarRoda;

import java.util.ArrayList;
import java.util.List;

public class OperacaoRoda {
    public FirebaseDatabase fb;
    public DatabaseReference dr;
    public FirebaseStorage fs;
    public StorageReference sr;
    public Context context;
    public Roda roda;
    public boolean editando;
    public int rodaPosition;

    public OperacaoUsuario ou;
    RodaAdapter adapter;
    public GoogleMap map;

    Usuario usuario;


    static List<Roda>rodas = new ArrayList<>();
    public OperacaoRoda(Context context){
        FirebaseDB.init();
        FirebaseSG.init();
        this.context = context;
        this.fb = FirebaseDB.getFb();
        this.dr = FirebaseDB.getDr("usuarios");

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
    public void inserir(Roda r, Button btn) {
        setCadastrar(btn,false);
        this.roda = r;
        for(Usuario u:ou.usuarios){
            if(u.getId().equals(Login.usuario.getId())){
                usuario = u;
                break;
            }
        }
        if(editando){
            usuario.getRoda().set(rodaPosition,roda);
        }else{
            usuario.getRoda().add(roda);
        }



        dr.child(usuario.getId()).setValue(usuario);
        Toast.makeText(context,"Salvo com sucesso",Toast.LENGTH_SHORT).show();
        setCadastrar(btn,true);
        ((Activity)context).finish();
    }

    public void listar(final GoogleMap m, final RecyclerView lstRodas){
        this.map = m;
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(m == null){
                    rodas.clear();

                    Log.i("MRSCRIPT","entrou no if");
                    for(DataSnapshot dsLIsta:dataSnapshot.getChildren()){
                        Usuario u = dsLIsta.getValue(Usuario.class);
                        if(Login.usuario != null && u.getId().equals(Login.usuario.getId()))
                        {
                            Login.usuario = u;

                        }
                        if(u.getRoda() != null)
                        {
                            for(Roda r:u.getRoda()){
                                rodas.add(r);
                            }

                        }


                    }
                    if(lstRodas != null){
                        adapter = new RodaAdapter(context,rodas);
                        lstRodas.setAdapter(adapter);
                    }
                }else{
                    rodas.clear();
                    Mapa.mMap.clear();
                    for(DataSnapshot dsLista:dataSnapshot.getChildren()){

                        Usuario u = dsLista.getValue(Usuario.class);
                        if(Login.usuario != null && u.getId().equals(Login.usuario.getId())){
                            Login.usuario = u;
                        }
                        if(u.getRoda() != null){
                            for(Roda r:u.getRoda()){
                                rodas.add(r);

                                LatLng latLng = new LatLng(Double.valueOf(r.getLatitude()), Double.valueOf(r.getLongitude()));
                                inserirMarker(latLng,r.getGrupoOrganizador());
                            }

                        }
                    }



                    Mapa.mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            LatLng ll = marker.getPosition();


                            for(Roda r:rodas){
                                if(r.getLatitude().toLowerCase().contains(""+ll.latitude) && r.getLongitude().toLowerCase().contains(""+ll.longitude)){
                                    VisualizarRoda.setRoda(r);
                                    context.startActivity(new Intent(context,VisualizarRoda.class));
                                }

                            }
                        }
                    });
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    private void inserirMarker(LatLng point,String titulo) {
        try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(titulo);
            markerOptions.position(point);

            Mapa.mMap.addMarker(markerOptions);
        } catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void exibirMensagem(String mensagem){
        Toast.makeText(context,mensagem,Toast.LENGTH_SHORT).show();
    }

    public void filter(String texto,String dia){
        List<Roda> rodasFilter = new ArrayList<>();
        for(Roda r:rodas){
            if(dia.equals("")){
                if(r.getComplemento().toLowerCase().contains(texto)||r.getRua().toLowerCase().contains(texto)||r.getCidade().toLowerCase().contains(texto)||r.getBairro().toLowerCase().contains(texto)||r.getEstado().toLowerCase().contains(texto)|| r.getTelefone().toLowerCase().contains(texto) || r.getHorario().toLowerCase().contains(texto)|| r.getGrupoOrganizador().toLowerCase().contains(texto) || r.getResponsavel().toLowerCase().contains(texto)){
                    rodasFilter.add(r);

                }
            }else{
                if(texto.equals("")){
                    if(r.getDiaDaSemana().toLowerCase().contains(dia)){
                        rodasFilter.add(r);

                    }
                }else{
                    if(r.getDiaDaSemana().toLowerCase().contains(dia)){

                        if(r.getComplemento().toLowerCase().contains(texto)||r.getRua().toLowerCase().contains(texto)||r.getCidade().toLowerCase().contains(texto)||r.getBairro().toLowerCase().contains(texto)||r.getEstado().toLowerCase().contains(texto)|| r.getTelefone().toLowerCase().contains(texto) || r.getHorario().toLowerCase().contains(texto)|| r.getGrupoOrganizador().toLowerCase().contains(texto) || r.getResponsavel().toLowerCase().contains(texto)){
                            rodasFilter.add(r);

                        }
                    }

                }

            }

        }
        adapter.filterList(rodasFilter);
    }
}
