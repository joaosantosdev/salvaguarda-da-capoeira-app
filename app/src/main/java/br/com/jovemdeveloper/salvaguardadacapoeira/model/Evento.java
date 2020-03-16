package br.com.jovemdeveloper.salvaguardadacapoeira.model;

public class Evento {

    public String key;
    public String descricao;
    public String url;
    public String nomeDoProprietario;
    public String emailDoProprietario;
    public String timeStamp;
    public String timeStampRemove;

    public String getTimeStampRemove() {
        return timeStampRemove;
    }

    public void setTimeStampRemove(String timeStampRemove) {
        this.timeStampRemove = timeStampRemove;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNomeDoProprietario() {
        return nomeDoProprietario;
    }

    public void setNomeDoProprietario(String nomeDoProprietario) {
        this.nomeDoProprietario = nomeDoProprietario;
    }

    public String getEmailDoProprietario() {
        return emailDoProprietario;
    }

    public void setEmailDoProprietario(String emailDoProprietario) {
        this.emailDoProprietario = emailDoProprietario;
    }
}
