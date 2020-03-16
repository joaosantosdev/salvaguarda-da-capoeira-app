package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;

import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoRoda;

import java.util.ArrayList;

public class VisualizarRodaLista extends AppCompatActivity implements View.OnFocusChangeListener, SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {

    private RecyclerView lstRodas;
    private OperacaoRoda or;
    SearchView search;
Spinner srchSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_roda_lista);
        or = new OperacaoRoda(this);
        configurar();
        search = (SearchView)findViewById(R.id.srchRoda);
        search.setOnQueryTextListener(this);
        srchSpinner = (Spinner)findViewById(R.id.srchSpinner);
        ArrayList<String> diaDaSemana = new ArrayList<String>();
        diaDaSemana.add("Dia");
        diaDaSemana.add("Segunda-Feira");
        diaDaSemana.add("Terça-Feira");
        diaDaSemana.add("Quarta-Feira");
        diaDaSemana.add("Quinta-Feira");
        diaDaSemana.add("Sexta-Feira");
        diaDaSemana.add("Sábado");
        diaDaSemana.add("Domingo");

        ArrayAdapter<String> adpDiaDaSemana = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,diaDaSemana);
        srchSpinner.setAdapter(adpDiaDaSemana);
        srchSpinner.setOnItemSelectedListener(this);
    }


    public void configurar(){

        lstRodas = (RecyclerView)findViewById(R.id.lstRodas);

        lstRodas.setHasFixedSize(true);
        // adapter.setViewBinder(new ViewBinderAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lstRodas.setLayoutManager(layoutManager);
        or.listar(null,lstRodas);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String spnText = srchSpinner.getSelectedItem().toString();
        if(spnText.equals("Dia")){
            spnText = "";
        }
        or.filter(newText.toLowerCase(),spnText.toLowerCase());
        return true;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    public void finalizar(){
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String newText = search.getQuery().toString();

        String spnText = srchSpinner.getSelectedItem().toString();
        if(spnText.equals("Dia")){
            spnText = "";
        }
        or.filter(newText.toLowerCase(),spnText.toLowerCase());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
