package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoUsuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;

public class CadastrarUsuario extends AppCompatActivity {
    private EditText email,nome,senha,rsenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_usuario);
        this.email = findViewById(R.id.cadastrar_email);
        this.nome = findViewById(R.id.cadastrar_nome);
        this.senha = findViewById(R.id.cadastrar_senha);
        this.rsenha = findViewById(R.id.cadastrar_rsenha);
    }

    public void cadastrarUsuario(View view){
        String semail = email.getText().toString();
        String ssenha = senha.getText().toString();
        String srsenha = rsenha.getText().toString();
        String snome = nome.getText().toString();
        if(!semail.trim().equals("") && !ssenha.trim().equals("") &&  !srsenha.trim().equals("") && !snome.equals("")){
            if(ssenha.equals(srsenha)){
                Usuario u = new Usuario();
                u.setId(String.valueOf(System.currentTimeMillis()));
                u.setEmail(semail);
                u.setNome(snome);
                u.setSenha(ssenha);
                OperacaoUsuario ou = new OperacaoUsuario(this);
                ou.inserir(u);
                exibirMensagem("Inserido com sucesso");
                finish();
            }else{
                exibirMensagem("Senhas não correspondem");
            }


        }else{
            if(snome.trim().equals("")){
                nome.requestFocus();
            }
            else if(semail.trim().equals("")){
                email.requestFocus();
            }else if(ssenha.trim().equals("")){
                senha.requestFocus();
            }else{
                rsenha.requestFocus();
            }
            exibirMensagem("Pŕeencha os campos corretamente");
        }
    }

    public void exibirMensagem(String mensagem){
        Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();
    }

}
