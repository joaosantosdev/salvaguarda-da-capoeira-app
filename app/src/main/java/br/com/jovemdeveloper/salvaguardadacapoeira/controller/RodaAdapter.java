package br.com.jovemdeveloper.salvaguardadacapoeira.controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.model.Roda;
import br.com.jovemdeveloper.salvaguardadacapoeira.view.Mapa;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RodaAdapter extends RecyclerView.Adapter<RodaAdapter.ViewHolderRoda>{

    private List<Roda> rodas;

    private Context context;
    private ImageLoader imageLoader;

    public RodaAdapter(Context context,List<Roda> rodas){
        this.rodas = rodas;
        this.context = context;
        this.imageLoader = imageLoader;

    }



    @Override
    public RodaAdapter.ViewHolderRoda onCreateViewHolder(ViewGroup group, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(group.getContext());
        View view = layoutInflater.inflate(R.layout.layout_lista_roda,group,false);
        ViewHolderRoda holderRoda = new ViewHolderRoda(view);

        return holderRoda;
    }

    @Override
    public void onBindViewHolder(RodaAdapter.ViewHolderRoda holder, final int position) {
        if(rodas != null & rodas.size() > 0){

            try{



                holder.dia.setText(rodas.get(position).getDiaDaSemana());
                holder.telefone.setText(rodas.get(position).getTelefone());
                holder.horario.setText(rodas.get(position).getHorario());
                holder.grupo.setText(rodas.get(position).getGrupoOrganizador());
                holder.responsavel.setText(rodas.get(position).getResponsavel());
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((Activity)context).finish();
                        Mapa.verNoMapa(new LatLng(Double.valueOf(rodas.get(position).getLatitude()),Double.valueOf(rodas.get(position).getLongitude())));
                    }
                });



            }catch (Exception e){

            }

        }
    }

    @Override
    public int getItemCount() {
        return rodas.size();
    }

    public class ViewHolderRoda extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dia,telefone,horario,grupo,responsavel;
        Button btn;

        public ViewHolderRoda(View view) {
            super(view);

            dia = (TextView) view.findViewById(R.id.txtDia_layout_lista);
            telefone = (TextView) view.findViewById(R.id.txtTelefone_layout_lista);
            horario = (TextView) view.findViewById(R.id.txtHorario_layout_lista);
            responsavel = (TextView) view.findViewById(R.id.txtResponsavel_layout_lista);
            grupo = (TextView) view.findViewById(R.id.txtGrupo_layout_lista);
            btn = (Button)view.findViewById(R.id.btnVerNoMapa);
            btn.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
    /*public void setFiltro(List<Grupo> listaFiltrada){
        this.grupos = new ArrayList<>();
        this.grupos.addAll(listaFiltrada);
        notifyDataSetChanged();
    }*/

    public void filterList(List<Roda> listaFiltrada){
        this.rodas = listaFiltrada;
        notifyDataSetChanged();
    }
}
