package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;

import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoUsuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Usuario;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DialogInterface.OnClickListener{
SharedPreferences preferences;
SharedPreferences.Editor editor;
static String nome;

Mapa mapa;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("usuario",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        mapa = new Mapa();

        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences("usuario",MODE_PRIVATE);

        if(preferences.getString("id",null) != null)
        {
            for(Usuario u:OperacaoUsuario.usuarios)
            {
                if(u.getId().equals(preferences.getString("id",null)) )
                {
                    Login.usuario = u;
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content,mapa,"Mapa");
        transaction.commitAllowingStateLoss();
       /* if(preferences.getBoolean("aviso",true)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aviso");
            builder.setMessage("Cada usuário só poderá inserir um campoeirista, um grupo e uma roda.");
            builder.create();
            builder.show();
            editor.putBoolean("aviso",false);
            editor.apply();
        }*/


    }
    EditText pais;
    EditText estado;
    EditText cidade;
     EditText bairro;
    EditText rua;
    AlertDialog dialog;
    public void getLocation(View view){
try {
    EditText location = (EditText)findViewById(R.id.edtLo);
    if(!location.getText().toString().trim().equals("")){
        int d = 4;
        if(location.getText().toString().contains(",")){
            String arr[] = location.getText().toString().split(",");
            if(arr.length == 2){
            d = 7;
            }else if(arr.length == 3){
                d = 12;
            }else{
                d = 14;
            }


        }
        mapa.searchLocation(location.getText().toString(),d);
    }else{
        Toast.makeText(getApplicationContext(), "Campo vazio", Toast.LENGTH_SHORT).show();
    }
    /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater layoutInflater = LayoutInflater.from(this);
    View v = layoutInflater.inflate(R.layout.layout_pesquisar_roda, null);


    pais = (EditText) v.findViewById(R.id.psqRodaPais);
    estado = (EditText) v.findViewById(R.id.psqRodaEstado);
    cidade = (EditText) v.findViewById(R.id.psqRodaCidade);
    bairro = (EditText) v.findViewById(R.id.psqRodaBairro);
    rua = (EditText) v.findViewById(R.id.psqRodaRua);
    Button btnPsq = (Button) v.findViewById(R.id.btnPsq);
    btnPsq.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //if (!pais.getText().toString().trim().equals("") && !estado.getText().toString().trim().equals("") && !cidade.getText().toString().trim().equals("") && !bairro.getText().toString().trim().equals("") && !rua.getText().toString().trim().equals("")) {

            if(estado.getText().toString().equals("") && cidade.getText().toString().equals("")){
                mapa.searchLocation(pais.getText().toString() + "," + estado.getText().toString() + "," + cidade.getText().toString() + "," + bairro.getText().toString() + "," + rua.getText().toString(),4);
            }


                dialog.dismiss();
           // } else {
                //Toast.makeText(getApplicationContext(), "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
            //}
        }
    });
    builder.setView(v);
     dialog = builder.create();
    dialog.show();*/


}catch (Exception e){
    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
}
}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



   // @Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
       //     return true;
       // }

      //  return super.onOptionsItemSelected(item);
   // }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.cadastrarCapoeirista) {
            if(preferences.getString("id","") != "" ){

                Intent it = new Intent(this, CadastrarCapoeirista.class);
                startActivity(it);
            }else{
                Toast.makeText(this,"Faça login para cadastrar capoeirista",Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.cadastrarGrupo) {

            if(preferences.getString("id","") != "" ){
                Intent it = new Intent(this,CadastrarGrupo.class);
                startActivity(it);
            }else{
                Toast.makeText(this,"Faça login para cadastrar grupo",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.cadastrarRodas) {

            if(preferences.getString("id","") != "" ){
                criarDialog().show();
            }else{
                Toast.makeText(this,"Faça login para cadastrar roda",Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.visualizarCapoeirista) {
            Intent it = new Intent(this, VisualizarCapoeiristas.class);
            startActivity(it);
        } else if (id == R.id.visualizarRoda) {
            Intent it = new Intent(this, VisualizarRodaLista.class);
            startActivity(it);
        }else if (id == R.id.visualizarGrupos) {
            Intent it = new Intent(this, VisualizarGrupos.class);
            startActivity(it);
        }else if(id == R.id.visualizarHistoria){
            Intent it = new Intent(this,Historia.class);
            startActivity(it);
        }else if (id == R.id.desenvolvedores) {
            Intent it = new Intent(this, Desenvolvedores.class);
            startActivity(it);
        }else if (id == R.id.login) {
            Intent it = new Intent(this, Login.class);
            startActivity(it);
        }else if(id == R.id.addEvento){
            if(preferences.getString("id","") != "" ) {
                Intent it = new Intent(this, AdicionarEvento.class);
                startActivity(it);
            }else{
                Toast.makeText(this,"Faça login para adicionar evento",Toast.LENGTH_SHORT).show();
            }
        }else if(id == R.id.visualizarEventos){
            Intent it = new Intent(this, VisualizarEventos.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private AlertDialog criarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[]   ops = new CharSequence[1];
        ops[0] = Html.fromHtml("<b><font color='#3E0001'>Cadastrar Roda</font></b>");
        if(Login.usuario.getRoda().size() > 0){
            int size = Login.usuario.getRoda().size()+1;
            ops = new CharSequence[size];
           ops[0] = "Cadastrar Roda";
            for(int i = 0;i < Login.usuario.getRoda().size();i++){
                ops[(i+1)] ="Editar Roda - "+Login.usuario.getRoda().get(i).getGrupoOrganizador()+"";
            }
        }

        builder.setTitle("Opções");
        builder.setItems(ops,this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int item) {
        Intent it = new Intent(this,CadastrarRoda.class);
        switch (item){
            case 0:
                CadastrarRoda.editando = false;
                startActivity(it);
                break;
            default:
                CadastrarRoda.editando = true;
                CadastrarRoda.rodaPosition = (item-1);
                startActivity(it);
                break;
        }
    }


}
