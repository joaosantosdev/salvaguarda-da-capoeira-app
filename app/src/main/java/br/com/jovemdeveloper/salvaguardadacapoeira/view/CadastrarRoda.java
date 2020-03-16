package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import br.com.jovemdeveloper.salvaguardadacapoeira.R;

import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoRoda;
import br.com.jovemdeveloper.salvaguardadacapoeira.database.CapoeiraDB;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Roda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CadastrarRoda extends AppCompatActivity {
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Geocoder geocoder;
    private CapoeiraDB db;
    private Roda roda;
    private OperacaoRoda or;
    public static EditText edtLatitude,edtLongitude,edtEstado,edtCidade,edtBairro,edtRua,edtComplemeto,edtTelefoneRoda;
    private EditText edtHorarioRoda,edtResponsavelRoda,edtOrganizadorRoda;
    private SharedPreferences preferences;
    private Spinner spnDiaSemana;
    private SharedPreferences.Editor editor;
    public static boolean editando;
    private Button button;
    public static int rodaPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_roda);
        db = new CapoeiraDB();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentRoda,new MapaRoda(),"MapaRoda");
        transaction.commitAllowingStateLoss();
        edtLatitude = (EditText)findViewById(R.id.edtLatitude);
        edtLongitude = (EditText)findViewById(R.id.edtLongitude);
        edtEstado = (EditText)findViewById(R.id.edtEstadoRoda);
        edtCidade = (EditText)findViewById(R.id.edtCidadeRoda);
        edtBairro = (EditText)findViewById(R.id.edtBairroRoda);
        edtRua = (EditText)findViewById(R.id.edtRuaRoda);
        edtComplemeto = (EditText)findViewById(R.id.edtComplementoRoda);
         button = (Button)findViewById(R.id.btnCadastrarRoda);

        spnDiaSemana = (Spinner) findViewById(R.id.spnDiaSemana);
        ArrayList<String> diaDaSemana = new ArrayList<String>();
        diaDaSemana.add("Segunda-Feira");
        diaDaSemana.add("Terça-Feira");
        diaDaSemana.add("Quarta-Feira");
        diaDaSemana.add("Quinta-Feira");
        diaDaSemana.add("Sexta-Feira");
        diaDaSemana.add("Sábado");
        diaDaSemana.add("Domingo");

        ArrayAdapter<String> adpDiaDaSemana = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,diaDaSemana);
        spnDiaSemana.setAdapter(adpDiaDaSemana);

        edtHorarioRoda = (EditText)findViewById(R.id.edtHorarioRoda);
        edtResponsavelRoda = (EditText)findViewById(R.id.edtResponsavelRoda);
        edtOrganizadorRoda = (EditText)findViewById(R.id.edtGrupoOrganizador);
        edtTelefoneRoda = (EditText)findViewById(R.id.edtTelefoneRoda);


        edtLatitude.setEnabled(false);
        edtLongitude.setEnabled(false);

        preferences = getSharedPreferences("usuario",MODE_PRIVATE);
        editor = preferences.edit();
        or = new OperacaoRoda(this);
        if(Login.usuario.getRoda().size() > 0 && editando){
            initRoda();

        }
    }
    public void initRoda(){
        edtLatitude.setText(Login.usuario.getRoda().get(rodaPosition).getLatitude());
        edtLongitude.setText(Login.usuario.getRoda().get(rodaPosition).getLongitude());
        edtEstado.setText(Login.usuario.getRoda().get(rodaPosition).getEstado());
        edtCidade.setText(Login.usuario.getRoda().get(rodaPosition).getCidade());
        edtBairro.setText(Login.usuario.getRoda().get(rodaPosition).getBairro());
        edtRua.setText(Login.usuario.getRoda().get(rodaPosition).getRua());
        edtComplemeto.setText(Login.usuario.getRoda().get(rodaPosition).getComplemento());



        String dia =Login.usuario.getRoda().get(rodaPosition).getDiaDaSemana();

        if(dia.equals("Segunda-Feira")){
            this.spnDiaSemana.setSelection(0);
        }else if(dia.equals("Terça-Feira")){
            this.spnDiaSemana.setSelection(1);
        }else if(dia.equals("Quarta-Feira")){
            this.spnDiaSemana.setSelection(2);
        }else if(dia.equals("Quinta-Feira")){
            this.spnDiaSemana.setSelection(3);
        }else if(dia.equals("Sexta-Feira")){
            this.spnDiaSemana.setSelection(4);
        }else if(dia.equals("Sábado")){
            this.spnDiaSemana.setSelection(5);
        }else{
            this.spnDiaSemana.setSelection(6);
        }

        edtHorarioRoda.setText(Login.usuario.getRoda().get(rodaPosition).getHorario());
        edtResponsavelRoda.setText(Login.usuario.getRoda().get(rodaPosition).getResponsavel());
        edtOrganizadorRoda.setText(Login.usuario.getRoda().get(rodaPosition).getGrupoOrganizador());

        edtTelefoneRoda.setText(Login.usuario.getRoda().get(rodaPosition).getTelefone());

    }

    public void obterLatLong(View view){
    if( !edtEstado.getText().toString().trim().equals("") && !edtCidade.getText().toString().trim().equals("") && !edtBairro.getText().toString().trim().equals("") && !edtRua.getText().toString().trim().equals("")){
        try {
            geocoder = new Geocoder(CadastrarRoda.this);
            String local = edtRua.getText().toString()+","+edtBairro.getText().toString()+","+edtCidade.getText().toString()+","+edtEstado.getText().toString()+",Brasil";
            List<Address> enderecoLista = geocoder.getFromLocationName(local,1);

            LatLng latLng = new LatLng(enderecoLista.get(0).getLatitude(),enderecoLista.get(0).getLongitude());
            edtLatitude.setText(""+latLng.latitude);
            edtLongitude.setText(""+latLng.longitude);
            MapaRoda.addMarker(latLng);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }else{
        if(edtEstado.getText().toString().trim().equals("")){
            edtEstado.requestFocus();
        }else if(edtCidade.getText().toString().trim().equals("")){
            edtCidade.requestFocus();
        }else if(edtBairro.getText().toString().trim().equals("")){
            edtBairro.requestFocus();
        }else if(edtRua.getText().toString().trim().equals("")){
            edtRua.requestFocus();
        }
    }


    }




    public void cadastrarRoda(View view) {
        if (!edtTelefoneRoda.getText().toString().trim().equals("") && !spnDiaSemana.getSelectedItem().toString().trim().equals("") && !edtHorarioRoda.getText().toString().trim().equals("") && !edtResponsavelRoda.getText().toString().trim().equals("") && !edtOrganizadorRoda.getText().toString().trim().equals("") && !edtEstado.getText().toString().trim().equals("") && !edtCidade.getText().toString().trim().equals("") && !edtBairro.getText().toString().trim().equals("") && !edtRua.getText().toString().trim().equals("") && !edtLatitude.getText().toString().trim().equals("") && !edtLongitude.getText().toString().trim().equals("")) {
            roda = new Roda();

            if(editando){

                Login.usuario.getRoda().get(rodaPosition).setId(String.valueOf(System.currentTimeMillis()));
                Login.usuario.getRoda().get(rodaPosition).setDiaDaSemana(spnDiaSemana.getSelectedItem().toString());
                Login.usuario.getRoda().get(rodaPosition).setHorario(edtHorarioRoda.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setGrupoOrganizador(edtOrganizadorRoda.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setResponsavel(edtResponsavelRoda.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setEstado(edtEstado.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setCidade(edtCidade.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setBairro(edtBairro.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setTelefone(edtTelefoneRoda.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setRua(edtRua.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setComplemento(!edtComplemeto.getText().toString().trim().equals("") ? edtComplemeto.getText().toString() : "");
                Login.usuario.getRoda().get(rodaPosition).setLatitude(edtLatitude.getText().toString());
                Login.usuario.getRoda().get(rodaPosition).setLongitude(edtLongitude.getText().toString());
                roda = Login.usuario.getRoda().get(rodaPosition);
                or.editando = true;
                or.rodaPosition = rodaPosition;
            }else{

                roda.setId(String.valueOf(System.currentTimeMillis()));
                roda.setDiaDaSemana(spnDiaSemana.getSelectedItem().toString());
                roda.setHorario(edtHorarioRoda.getText().toString());
                roda.setGrupoOrganizador(edtOrganizadorRoda.getText().toString());
                roda.setResponsavel(edtResponsavelRoda.getText().toString());
                roda.setEstado(edtEstado.getText().toString());
                roda.setCidade(edtCidade.getText().toString());
                roda.setBairro(edtBairro.getText().toString());
                roda.setTelefone(edtTelefoneRoda.getText().toString());
                roda.setRua(edtRua.getText().toString());
                roda.setComplemento(!edtComplemeto.getText().toString().trim().equals("") ? edtComplemeto.getText().toString() : "");
                roda.setLatitude(edtLatitude.getText().toString());
                roda.setLongitude(edtLongitude.getText().toString());
            }


            or.inserir(roda,button);





        } else {
            if (spnDiaSemana.getSelectedItem().toString().trim().equals("")) {
                spnDiaSemana.requestFocus();
            } else if (edtHorarioRoda.getText().toString().trim().equals("")) {
                edtHorarioRoda.requestFocus();
            } else if (edtOrganizadorRoda.getText().toString().trim().equals("")) {
                edtOrganizadorRoda.requestFocus();
            } else if (edtResponsavelRoda.getText().toString().trim().equals("")) {
                edtResponsavelRoda.requestFocus();
            } else if (edtEstado.getText().toString().trim().equals("")) {
                edtEstado.requestFocus();
            } else if (edtCidade.getText().toString().trim().equals("")) {
                edtCidade.requestFocus();
            } else if (edtBairro.getText().toString().trim().equals("")) {
                edtBairro.requestFocus();
            } else if (edtRua.getText().toString().trim().equals("")) {
                edtRua.requestFocus();
            } else if (edtLatitude.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Clique em Obter latitude", Toast.LENGTH_SHORT).show();
            } else if (edtLongitude.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Clique em Obter latitude", Toast.LENGTH_SHORT).show();
            } else if (edtTelefoneRoda.getText().toString().trim().equals("")) {
                edtTelefoneRoda.requestFocus();
            }
        }
    }


}
