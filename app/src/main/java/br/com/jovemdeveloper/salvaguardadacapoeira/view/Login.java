package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoUsuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.database.CapoeiraDB;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Capoeirista;

import java.util.List;
import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Roda;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;

public class Login extends AppCompatActivity {
    private CapoeiraDB db;
    private List<Capoeirista> capoeiristas;
    private List<Roda> rodas;
    private EditText email,senha;
    private Spinner loginTipo;
    private OperacaoUsuario ou;
    public static Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.login_email);
        senha = (EditText)findViewById(R.id.login_senha);
        ou = new OperacaoUsuario(this);
        ou.listar();


    }

    public void cadastrarGo(View view){
        startActivity(new Intent(this,CadastrarUsuario.class));
    }

    public void entrar(View v){
    boolean tem =false;
    for(Usuario u:ou.usuarios){
        if(u.getEmail().toLowerCase().equals(email.getText().toString().toLowerCase()) && u.getSenha().equals(senha.getText().toString()) ){
            exibirMensagem("logado com sucesso");
            SharedPreferences preferences = getSharedPreferences("usuario",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id",u.getId());
            editor.putString("email",u.getEmail());
            editor.putString("nome",u.getNome());
            editor.putString("senha",u.getSenha());
            usuario = u;
            editor.apply();
            tem =true;
            finish();


            break;
        }



    }

    if(!tem){
        exibirMensagem("Usuário não cadastrado");
    }




    }

    public void exibirMensagem(String mensagem){
        Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();
    }
}
