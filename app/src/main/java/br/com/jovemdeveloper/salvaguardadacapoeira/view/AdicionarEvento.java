package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoEvento;
import com.squareup.picasso.Picasso;

public class AdicionarEvento extends AppCompatActivity implements View.OnClickListener {



    private EditText edtDescricaoEvento;
    private ImageView imgEvento;
    private Uri uriImagem;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private OperacaoEvento oe;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_evento);
        edtDescricaoEvento = (EditText)findViewById(R.id.edtDescricaoEvento);
        imgEvento = (ImageView)findViewById(R.id.imgEvento);
        preferences = getSharedPreferences("usuario",MODE_PRIVATE);
        editor = preferences.edit();
        oe = new OperacaoEvento(this,preferences,editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      btn = (Button) findViewById(R.id.fab);
        btn.setOnClickListener(this);


    }

    public void selecionarImagemEvento(View view){
            Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            it.setType("image/*");
        startActivityForResult(it,0);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode == 0 && resultCode == RESULT_OK){
            uriImagem = intent.getData();
            Picasso.get().load(uriImagem).into(imgEvento);
        }

    }

    @Override
    public void onClick(View view) {
        String descricao = edtDescricaoEvento.getText().toString();
        String email = preferences.getString("email","");
        String nome = preferences.getString("nome","");
        if(!descricao.trim().equals("") && uriImagem != null){


            btn.setText("Adicionando...");
            btn.setEnabled(false);
            oe.upload(uriImagem,descricao,nome,email,btn);

        }else{
            if(descricao.trim().equals("")){


                edtDescricaoEvento.requestFocus();
                Toast.makeText(this,"Campo vazio",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Selecione uma imagem",Toast.LENGTH_SHORT).show();
            }

        }

    }
}
