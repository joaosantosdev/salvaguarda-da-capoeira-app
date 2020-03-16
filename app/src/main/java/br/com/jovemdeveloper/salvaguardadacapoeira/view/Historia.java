package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.HistoriaAdapter;

public class Historia extends AppCompatActivity {
    private ViewPager pagerHistoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

       HistoriaAdapter adapterHistoria = new HistoriaAdapter(this);
        pagerHistoria = (ViewPager)findViewById(R.id.pagerHistoria);
        pagerHistoria.setAdapter(adapterHistoria);


    }


}
