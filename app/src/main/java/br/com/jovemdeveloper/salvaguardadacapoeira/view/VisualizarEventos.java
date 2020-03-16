package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;

import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoEvento;

public class VisualizarEventos extends AppCompatActivity {

    private RecyclerView lstEventos;
    private OperacaoEvento oe;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_eventos);
        try{
            preferences = getSharedPreferences("usuario",MODE_PRIVATE);
            editor = preferences.edit();

        oe = new OperacaoEvento(this,preferences,editor);
        configurar();




        }catch (Exception e){
            Log.i("MRSCRIPT",e.toString());
        }
    }

    public void configurar(){
try{
    Log.i("MRSCRIPT","sadsa");

        lstEventos = (RecyclerView)findViewById(R.id.lstEventos);

        lstEventos.setHasFixedSize(true);
        // adapter.setViewBinder(new ViewBinderAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lstEventos.setLayoutManager(layoutManager);
        oe.listar(lstEventos);
}catch (Exception e){
    Log.i("MRSCRIPT",e.toString());
}
    }

}
