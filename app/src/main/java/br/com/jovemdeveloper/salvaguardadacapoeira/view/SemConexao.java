package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.database.CapoeiraDB;

public class SemConexao extends AppCompatActivity {
    CapoeiraDB db;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_conexao);
        String tipo = getIntent().getStringExtra("tipo");
        TextView tipoConexao = (TextView)findViewById(R.id.tipoConexao);
        tipoConexao.setText(tipo);
        btn = (Button)findViewById(R.id.btnTentar);

    }
    public void setEnable(boolean tipo){
        btn.setEnabled(tipo);
    }

    public void verificarConexao(View view){
        try{
            setEnable(false);
            db = new CapoeiraDB();
            Thread th = new Thread(){
                @Override
                public void run(){
                    final String resposta = db.verificarConexao();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(resposta.equals("1")){

                                Toast.makeText(getApplicationContext(),"Verifique sua conexão",Toast.LENGTH_SHORT).show();
                                setEnable(true);
                            }else if(resposta.equals("2")){
                                setEnable(true);
                                Toast.makeText(getApplicationContext(),"Sem conexão",Toast.LENGTH_SHORT).show();

                            }else if(resposta.equals("3")){
                                setEnable(true);
                                Toast.makeText(getApplicationContext(),"Sem conexão",Toast.LENGTH_SHORT).show();
                            }else if(resposta.equals("conectado")){
                                setEnable(true);
                                finish();
                                Intent it = new Intent(getApplicationContext(),Principal.class);
                                startActivity(it);
                            }else{
                                setEnable(true);
                                Toast.makeText(getApplicationContext(),"Sem conexão",Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            };th.start();
        }catch (Exception e){

        }
    }
}
