package br.com.jovemdeveloper.salvaguardadacapoeira.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String id;
    private String email;
    private String nome;
    private String senha;
    private Grupo Grupo;
    private List<Roda> roda = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    private Capoeirista capoeirista;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public br.com.jovemdeveloper.salvaguardadacapoeira.model.Grupo getGrupo() {
        return Grupo;
    }

    public void setGrupo(br.com.jovemdeveloper.salvaguardadacapoeira.model.Grupo grupo) {
        Grupo = grupo;
    }

    public List<Roda> getRoda() {
        return roda;
    }

    public void setRoda(List<Roda> roda) {
        this.roda = roda;
    }

    public Capoeirista getCapoeirista() {
        return capoeirista;
    }

    public void setCapoeirista(Capoeirista capoeirista) {
        this.capoeirista = capoeirista;
    }
}
