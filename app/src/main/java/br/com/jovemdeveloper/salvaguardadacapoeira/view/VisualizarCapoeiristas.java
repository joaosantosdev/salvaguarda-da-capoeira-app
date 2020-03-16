package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoCapoeirista;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Capoeirista;


import java.util.ArrayList;
import java.util.List;

public class VisualizarCapoeiristas extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<Capoeirista> capoeiristas = new ArrayList<Capoeirista>();
    RecyclerView lstCapoeiristas;

    SearchView search;

    OperacaoCapoeirista oc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_capoeiristas);
        search = (SearchView) findViewById(R.id.srchCapoeirista);
        search.setOnQueryTextListener(this);
        configurar();


    }


    public void configurar() {
        oc = new OperacaoCapoeirista(this);


        lstCapoeiristas = (RecyclerView) findViewById(R.id.lstCapoeiristas);

        lstCapoeiristas.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lstCapoeiristas.setLayoutManager(layoutManager);
        oc.listar(lstCapoeiristas);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        oc.filter(newText);
        return true;
    }
}
