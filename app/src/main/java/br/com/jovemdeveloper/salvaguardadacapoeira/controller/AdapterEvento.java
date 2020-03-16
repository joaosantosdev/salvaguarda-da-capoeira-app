package br.com.jovemdeveloper.salvaguardadacapoeira.controller;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Evento;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterEvento extends RecyclerView.Adapter<AdapterEvento.ViewHolderImagem>{
        private List<Evento> eventos;
    private Context context;

    public AdapterEvento(Context context, List<Evento> e){
        this.eventos = e;

        Log.i("MrScript","contur");
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolderImagem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_visualizar_eventos,parent,false);
        ViewHolderImagem holder = new ViewHolderImagem(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderImagem holder, int position) {

        Evento e = eventos.get(position);
        holder.txtDescricao.setText(e.getDescricao());
        holder.txtNome.setText(e.getNomeDoProprietario());
        holder.txtEmail.setText(e.getEmailDoProprietario());
        Picasso.get().load(e.getUrl()).fit().centerInside().into(holder.ivImg);




    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }


    public class ViewHolderImagem extends RecyclerView.ViewHolder{
        ImageView ivImg;
        TextView txtDescricao,txtNome,txtEmail;

        public ViewHolderImagem(View view) {
            super(view);
            ivImg = (ImageView)view.findViewById(R.id.img_layout_evento);
            txtDescricao = (TextView) view.findViewById(R.id.descricao_layout_evento);
            txtNome = (TextView) view.findViewById(R.id.nome_layout_evento);
            txtEmail = (TextView) view.findViewById(R.id.email_layout_evento);


        }




    }



}
