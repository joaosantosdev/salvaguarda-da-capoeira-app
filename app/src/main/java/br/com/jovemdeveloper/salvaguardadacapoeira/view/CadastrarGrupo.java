package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoGrupo;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Grupo;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class CadastrarGrupo extends AppCompatActivity {
    private EditText edtNomeGrupo,edtNomeResponsavel,edtMestreGrupo,edtEstadoGrupo,edtCidadeGrupo,edtBairroGrupo,edtRuaGrupo,edtComplemento;
    private   Grupo grupo;
    private final int PERMISION_REQUEST = 13;
    private final int INTENT_FOTO = 13;
    private CircleImageView fotoGrupo = null;
    private Uri imagemSelecionada =null;
    private OperacaoGrupo og;
    private String imgDefault = "NULL";
    private SharedPreferences preferences;
    private Button button;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_grupo);
        og = new OperacaoGrupo(this);
        permissao();
        this.edtNomeGrupo = (EditText)findViewById(R.id.edtNomeGrupo);
        this.edtNomeResponsavel = (EditText)findViewById(R.id.edtNomeResponsavel);
        this.edtMestreGrupo = (EditText)findViewById(R.id.edtMestreGrupo);
        this.edtEstadoGrupo = (EditText)findViewById(R.id.edtEstadoGrupo);
        this.edtCidadeGrupo = (EditText)findViewById(R.id.edtCidadeGrupo);
        this.edtBairroGrupo = (EditText)findViewById(R.id.edtBairroGrupo);
        this.edtRuaGrupo = (EditText)findViewById(R.id.edtRuaGrupo);
        this.edtComplemento = (EditText)findViewById(R.id.edtComplementoGrupo);
        this.button= (Button)findViewById(R.id.btnCadastrarGrupo);
        fotoGrupo = (CircleImageView)findViewById(R.id.ivFotoGrupo);

        if(Login.usuario.getGrupo() != null){
            initGrupo();

        }

    }

    public void initGrupo(){

        try {
            this.edtNomeGrupo.setText(Login.usuario.getGrupo().getNomeGrupo());
            this.edtNomeResponsavel.setText(Login.usuario.getGrupo().getNomeResponsavel());

            this.edtMestreGrupo.setText(Login.usuario.getGrupo().getMestreGrupo());
            this.edtEstadoGrupo.setText(Login.usuario.getGrupo().getEstado());
            this.edtCidadeGrupo.setText(Login.usuario.getGrupo().getCidade());
            this.edtBairroGrupo.setText(Login.usuario.getGrupo().getBairro());
            this.edtRuaGrupo.setText(Login.usuario.getGrupo().getRua());
            this.edtComplemento.setText(Login.usuario.getGrupo().getComplemento());
            imgDefault = Login.usuario.getGrupo().getUrlImagem();
            if (!imgDefault.equals("NULL")) {
                Picasso.get().load(imgDefault).into(fotoGrupo);
            }

        }catch(Exception e){

        }

    }

    public void permissao(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

        }if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

        }else{


            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISION_REQUEST);



        }
    }
    public void cadastrarGrupo(View view){






        if(!this.edtNomeGrupo.getText().toString().trim().equals("") && !this.edtNomeResponsavel.getText().toString().trim().equals("") && !this.edtMestreGrupo.getText().toString().trim().equals("")  && !this.edtEstadoGrupo.getText().toString().trim().equals("") && !this.edtCidadeGrupo.getText().toString().trim().equals("") && !this.edtBairroGrupo.getText().toString().trim().equals("") && !this.edtRuaGrupo.getText().toString().trim().equals("")){


            try{

                grupo = new Grupo();


                grupo.setId(Login.usuario.getId());
                grupo.setNomeGrupo(this.edtNomeGrupo.getText().toString());
                grupo.setNomeResponsavel(this.edtNomeResponsavel.getText().toString());
                grupo.setMestreGrupo(this.edtMestreGrupo.getText().toString());
                grupo.setEstado(this.edtEstadoGrupo.getText().toString());
                grupo.setCidade(this.edtCidadeGrupo.getText().toString());
                grupo.setBairro(this.edtBairroGrupo.getText().toString());
                grupo.setRua(this.edtRuaGrupo.getText().toString());
                grupo.setComplemento(this.edtComplemento.getText().toString());




                og.inserir(grupo,imagemSelecionada,imgDefault,button);


            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }



        }else{

            if(this.edtNomeGrupo.getText().toString().trim().equals("")){
                this.edtNomeGrupo.requestFocus();
            }else if(this.edtNomeResponsavel.getText().toString().trim().equals("")){
                this.edtNomeResponsavel.requestFocus();
            }else if(this.edtMestreGrupo.getText().toString().trim().equals("")){
                this. edtMestreGrupo.requestFocus();
            }else if(this.edtEstadoGrupo.getText().toString().trim().equals("")){
                this.edtEstadoGrupo.requestFocus();
            }else if(this.edtCidadeGrupo.getText().toString().trim().equals("")){
                this.edtCidadeGrupo.requestFocus();
            }else if(this.edtBairroGrupo.getText().toString().trim().equals("")){
                this.edtBairroGrupo.requestFocus();
            }else{
                this.edtRuaGrupo.requestFocus();
            }


        }



    }


    public void limparCampos(){
        this.edtNomeGrupo.setText("");
        this.edtNomeResponsavel.setText("");
        this.edtMestreGrupo.setText("");
        this.edtEstadoGrupo.setText("");
        this.edtCidadeGrupo.setText("");
        this.edtBairroGrupo.setText("");
        this.edtRuaGrupo.setText("");
        this.edtComplemento.setText("");
        fotoGrupo.setImageResource(R.drawable.ic_inserir_foto_grupo);
        fotoGrupo = null;
    }

    public void selecionarFotoGrupo(View view){
        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        it.setType("image/*");
        startActivityForResult(it,INTENT_FOTO);
    }
    @Override
    public void onRequestPermissionsResult(int resultCode,String[] permissions,int [] grantResults){
        if(resultCode == PERMISION_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }else {

            }
            return;
        }
    }

    //pega imagem da galeria
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == INTENT_FOTO) {
                imagemSelecionada = intent.getData();
                Picasso.get().load(imagemSelecionada).into(fotoGrupo);

            }
        }
    }
}
