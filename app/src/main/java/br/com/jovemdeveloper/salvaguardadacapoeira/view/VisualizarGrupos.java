package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;

import br.com.jovemdeveloper.salvaguardadacapoeira.controller.GrupoAdapter;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoGrupo;

public class VisualizarGrupos extends AppCompatActivity implements SearchView.OnQueryTextListener {


private GrupoAdapter adapter;
private RecyclerView lstGrupos;
private OperacaoGrupo og;

    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_grupos);

        og = new OperacaoGrupo(this);
        search = (SearchView)findViewById(R.id.srchGrupo);
        search.setOnQueryTextListener(this);
       configurar();
    }


    public void configurar(){

        lstGrupos = (RecyclerView)findViewById(R.id.lstGrupos);

        lstGrupos.setHasFixedSize(true);
        // adapter.setViewBinder(new ViewBinderAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lstGrupos.setLayoutManager(layoutManager);
        og.listar(lstGrupos);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        og.filter(newText);
        return true;
    }
}
