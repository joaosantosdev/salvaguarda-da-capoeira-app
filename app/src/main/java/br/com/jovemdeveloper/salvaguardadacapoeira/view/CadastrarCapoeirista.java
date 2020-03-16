package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoCapoeirista;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoUsuario;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.PagerAdapterCapoeirista;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Capoeirista;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastrarCapoeirista extends AppCompatActivity {
    private ViewPagerCustomizado viewPager;
    private int ano,mes,dia;
    private final int INTENT_FOTO = 13;
    private CircleImageView fotoCapoeirista = null;
    private final int PERMISION_REQUEST = 2;
    private Uri imagemSelecionada;
    private PagerAdapterCapoeirista capoeiristaAdapter;
    private EditText nomeCapoeirista,cpfCapoeirista,rgCapoeirista,endereco,telefoneCapoeirista,apelidoCapoeirista,apelidoMestre,whatsappCapoeirista,grupoCapoeirista,nomeMestre;
    private Spinner sexoCapoeirista,graduacaoCapoeirista,estiloCapoeirista,graduacaoMestre;
    private Button anoGraduacaoCapoeirista,dataNascimentoCapoeirista;
    private OperacaoCapoeirista oc;
    private Capoeirista capoeirista;
    public String imgDefault = "NULL";
    private OperacaoUsuario ou;
    ArrayAdapter<String> adapterSexo,adapterGraduacao,adapterEstilos;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_capoeirista_c1);
        try {
            initWidgets();
            oc = new OperacaoCapoeirista(this);
            permissao();
            ou = new OperacaoUsuario(this);
            ou.listar();
            Calendar calendar = Calendar.getInstance();
            ano = calendar.get(Calendar.YEAR);
            mes = calendar.get(Calendar.MONTH);
            dia = calendar.get(Calendar.DAY_OF_MONTH);




            imagemSelecionada = null;
            fotoCapoeirista = (CircleImageView)findViewById(R.id.ivFotoCapoeirista);
           if(Login.usuario.getCapoeirista() != null){
               initCapoeirista();
           }

        }catch (Exception e){

        }

    }

    public void initCapoeirista(){




        this.nomeCapoeirista.setText(Login.usuario.getCapoeirista().getNome());
        this.cpfCapoeirista.setText(Login.usuario.getCapoeirista().getCpf());
        this.rgCapoeirista.setText(Login.usuario.getCapoeirista().getRg());
        this.dataNascimentoCapoeirista.setText(Login.usuario.getCapoeirista().getDataNascimento());
        if(Login.usuario.getCapoeirista().getSexo().equals("Masculino")){
            this.sexoCapoeirista.setSelection(0);
        }else if(Login.usuario.getCapoeirista().getSexo().equals("Feminino")){
            this.sexoCapoeirista.setSelection(1);
        }else{
            this.sexoCapoeirista.setSelection(2);
        }

        this.telefoneCapoeirista.setText(Login.usuario.getCapoeirista().getTelefone());
        this.endereco.setText(Login.usuario.getCapoeirista().getEndereco());
        this.apelidoCapoeirista.setText(Login.usuario.getCapoeirista().getApelido());
        this.whatsappCapoeirista.setText(Login.usuario.getCapoeirista().getWhatsapp());

        if(Login.usuario.getCapoeirista().getGraduacao().equals("Mestre")){
            this.graduacaoCapoeirista.setSelection(0);
        }else if(Login.usuario.getCapoeirista().getGraduacao().equals("Contramestre")){
            this.graduacaoCapoeirista.setSelection(1);
        }else if(Login.usuario.getCapoeirista().getGraduacao().equals("Professor")){
            this.graduacaoCapoeirista.setSelection(2);
        }else if(Login.usuario.getCapoeirista().getGraduacao().equals("Treinel")){
            this.graduacaoCapoeirista.setSelection(3);
        }else if(Login.usuario.getCapoeirista().getGraduacao().equals("Aluno formado")){
            this.graduacaoCapoeirista.setSelection(4);
        }else{
            this.graduacaoCapoeirista.setSelection(5);
        }

        this.anoGraduacaoCapoeirista.setText(Login.usuario.getCapoeirista().getAnoGraduacao());
        this.grupoCapoeirista.setText(Login.usuario.getCapoeirista().getGrupo());

        if(Login.usuario.getCapoeirista().getEstilo().equals("Angola")){
            this.estiloCapoeirista.setSelection(0);
        }else if(Login.usuario.getCapoeirista().getEstilo().equals("Regional")){
            this.estiloCapoeirista.setSelection(1);
        }else if(Login.usuario.getCapoeirista().getEstilo().equals("Capoeira de Rua")){
            this.estiloCapoeirista.setSelection(2);
        }else if(Login.usuario.getCapoeirista().getEstilo().equals("Contemporânea")){
            this.estiloCapoeirista.setSelection(3);
        }else{
            this.estiloCapoeirista.setSelection(4);
        }

        this.nomeMestre.setText(Login.usuario.getCapoeirista().getNomeMestre());
        this.apelidoMestre.setText(Login.usuario.getCapoeirista().getApelidoMestre());



        if(Login.usuario.getCapoeirista().getGraduacaoMestre().equals("Mestre")){
            this.graduacaoMestre.setSelection(0);
        }else if(Login.usuario.getCapoeirista().getGraduacaoMestre().equals("Contramestre")){
            this.graduacaoMestre.setSelection(1);
        }else if(Login.usuario.getCapoeirista().getGraduacaoMestre().equals("Professor")){
            this.graduacaoMestre.setSelection(2);
        }else if(Login.usuario.getCapoeirista().getGraduacaoMestre().equals("Treinel")){
            this.graduacaoMestre.setSelection(3);
        }else if(Login.usuario.getCapoeirista().getGraduacaoMestre().equals("Aluno formado")){
            this.graduacaoMestre.setSelection(4);
        }else{
            this.graduacaoMestre.setSelection(5);
        }

        imgDefault = Login.usuario.getCapoeirista().getUrlImagem();
        if(!imgDefault.equals("NULL")){
            Picasso.get().load(imgDefault).into(fotoCapoeirista);
        }
    }

    public void initWidgets(){
        //pegando os valores dos campos
        this.button = (Button)findViewById(R.id.btnCadastrar);
        this.nomeCapoeirista = (EditText) findViewById(R.id.edtNomeCapoeirista);
        this.dataNascimentoCapoeirista = (Button)findViewById(R.id.btnDataNascimentoCapoeirista);
        this.sexoCapoeirista = (Spinner)findViewById(R.id.spnSexoCapoeirista);
        this.cpfCapoeirista = (EditText) findViewById(R.id.edtCpfCapoeirista);
        this.rgCapoeirista = (EditText) findViewById(R.id.edtRgCapoeirista);
        this.telefoneCapoeirista = (EditText) findViewById(R.id.edtTelefoneCapoeirista);
        this.apelidoCapoeirista = (EditText) findViewById(R.id.edtApelidoCapoeirista);
        this.whatsappCapoeirista = (EditText) findViewById(R.id.edtWhatsappCapoeirista);
        this.graduacaoCapoeirista = (Spinner)findViewById(R.id.spnGraduacaoCapoeirista);
        this.anoGraduacaoCapoeirista = (Button)findViewById(R.id.btnAnoGraduacaoCapoeirista);
        this.grupoCapoeirista = (EditText) findViewById(R.id.edtGrupoCapoeirista);
        this.estiloCapoeirista = (Spinner)findViewById(R.id.spnEstiloCapoeirista);
        this.nomeMestre = (EditText) findViewById(R.id.edtNomeMestre);
        this.apelidoMestre = (EditText) findViewById(R.id.edtApelidoMestre);
        this.graduacaoMestre = (Spinner)findViewById(R.id.spnGraduacaoMestre);
        this.endereco = (EditText) findViewById(R.id.edtEnderecoCapoeirista);


        if(Login.usuario.getGrupo() != null){
            grupoCapoeirista.setText(Login.usuario.getGrupo().getNomeGrupo());
        }



        String []nomes = new String[]{"Masculino","Feminino","Outro"};
        adapterSexo = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,nomes);
        sexoCapoeirista.setAdapter(adapterSexo);


        String graduacoes[] = {"Mestre","Contramestre","Professor","Treinel","Aluno formado","Aluno"};
        String estilos[] = {"Angola","Regional","Capoeira de Rua","Contemporânea","Capoeira"};

        adapterGraduacao = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,graduacoes);
        adapterEstilos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,estilos);
        graduacaoCapoeirista.setAdapter(adapterGraduacao);
        graduacaoMestre.setAdapter(adapterGraduacao);
        estiloCapoeirista.setAdapter(adapterEstilos);
    }



    public void exibirMensagem(String mensagem){
        Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();
    }
    public void cadastrar(View view){
       try {
           String nome = this.nomeCapoeirista.getText().toString();
           String nascimento = this.dataNascimentoCapoeirista.getText().toString();
           String a = this.apelidoCapoeirista.getText().toString();
           String nm = this.nomeMestre.getText().toString();
           String am = this.apelidoMestre.getText().toString();
           String g = this.grupoCapoeirista.getText().toString();

           if (!g.trim().equals("") && !nascimento.trim().equals("") &&!nome.trim().equals("") && !a.trim().equals("") && !nm.trim().equals("") && !am.trim().equals("")) {
             // setCadastrar(false);

              capoeirista = new Capoeirista();
               //Colocando os valores dos campos no objeto
               capoeirista.setId(Login.usuario.getId());
               capoeirista.setNome(this.nomeCapoeirista.getText().toString());
               capoeirista.setDataNascimento(this.dataNascimentoCapoeirista.getText().toString());
               capoeirista.setSexo(this.sexoCapoeirista.getSelectedItem().toString());
               capoeirista.setCpf(this.cpfCapoeirista.getText().toString().trim().equals("") ? "" : this.cpfCapoeirista.getText().toString());
               capoeirista.setRg(this.rgCapoeirista.getText().toString().trim().equals("") ? "" : this.rgCapoeirista.getText().toString());
               capoeirista.setTelefone(this.telefoneCapoeirista.getText().toString().trim().equals("") ? "" : this.telefoneCapoeirista.getText().toString());
               capoeirista.setApelido(this.apelidoCapoeirista.getText().toString());
               capoeirista.setWhatsapp(this.whatsappCapoeirista.getText().toString().trim().equals("") ? "" : this.whatsappCapoeirista.getText().toString());
               capoeirista.setGraduacao(this.graduacaoCapoeirista.getSelectedItem().toString());
               capoeirista.setAnoGraduacao(this.anoGraduacaoCapoeirista.getText().toString().trim().equals("") ? "" : this.anoGraduacaoCapoeirista.getText().toString());
               capoeirista.setGrupo(this.grupoCapoeirista.getText().toString().trim().equals("") ? "" : this.grupoCapoeirista.getText().toString());
               capoeirista.setEstilo(this.estiloCapoeirista.getSelectedItem().toString());
               capoeirista.setNomeMestre(this.nomeMestre.getText().toString());
               capoeirista.setApelidoMestre(this.apelidoMestre.getText().toString());
               capoeirista.setGraduacaoMestre(this.graduacaoMestre.getSelectedItem().toString());
               capoeirista.setEndereco(this.endereco.getText().toString().trim().equals("") ? "" : this.endereco.getText().toString());
               oc.inserir(capoeirista,imagemSelecionada,imgDefault,button);





           } else {
               if (nome.trim().equals("")) {
                   this.nomeCapoeirista.requestFocus();
               } else if (nascimento.trim().equals("")) {
                   this.dataNascimentoCapoeirista.requestFocus();
               }
               else if (a.trim().equals("")) {
                   this.apelidoCapoeirista.requestFocus();
               }else if(g.equals("")){
                   this.grupoCapoeirista.requestFocus();
               } else if (nm.trim().equals("")) {
                   this.nomeMestre.requestFocus();
               } else {
                   this.apelidoMestre.requestFocus();
               }
               Toast.makeText(getApplicationContext(), R.string.alerta_campo_vazio, Toast.LENGTH_SHORT).show();

           }
       }catch (Exception e){
           Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
       }

    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//
//        inflater.inflate(R.menu.menu_cadastrar_capoeirista,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        if(item.getItemId() == R.id.itemRemoverFoto){
//            imagemSelecionada= Uri.parse("NULL");
//            fotoCapoeirista.setImageResource(R.drawable.ic_inserir_foto);
//             imgDefault = "NULL";
//        }
//        return true;
//    }

    int idButton;

    //abrir dialog da data
    public void selecionarData(View v) {
        idButton = v.getId();
        showDialog(v.getId());
    }





    //criando dialog da data
    @Override
    public Dialog onCreateDialog(int id){

            return new DatePickerDialog(this,listenerData,ano,mes,dia);

    }

    DatePickerDialog.OnDateSetListener listenerData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int a, int m, int d) {
            ano = a;
            mes = m;
            dia = d;
           Button btn = (Button)findViewById(R.id.btnDataNascimentoCapoeirista);
            Button btn2 = (Button)findViewById(R.id.btnAnoGraduacaoCapoeirista);
           String data = "";


           if(dia < 10){
               data = "0"+dia+"/"+(mes+1)+"/"+ano;
               if(mes < 10){
                   data = "0"+dia+"/0"+(mes+1)+"/"+ano;
               }else{
                   data = "0"+dia+"/"+(mes+1)+"/"+ano;
               }
           }else{
               data = dia+"/"+(mes+1)+"/"+ano;
               if(mes < 10){
                   data = dia+"/0"+(mes+1)+"/"+ano;
               }else{
                   data = dia+"/"+(mes+1)+"/"+ano;
               }
           }
            Toast.makeText(getApplicationContext(), data,Toast.LENGTH_SHORT).show();
           if(idButton == R.id.btnDataNascimentoCapoeirista){
               btn.setText(data);
           }else{
               btn2.setText(data);
           }

        }
    };

    //abrir galeria
    public void selecionarFoto(View view){


        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        it.setType("image/*");
        startActivityForResult(it,INTENT_FOTO);
    }

    //permissao para abrir a galeria
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

                Picasso.get().load(imagemSelecionada).into(fotoCapoeirista);

            }
        }
    }

    public void permissao(){
        //Permissão para acessar a galeria
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

        }if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISION_REQUEST);


        }
    }





}
