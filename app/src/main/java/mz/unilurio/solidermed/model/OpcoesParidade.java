package mz.unilurio.solidermed.model;

public class OpcoesParidade {
    int valor;
    int id;

    public OpcoesParidade(int valor, int id) {
        this.valor = valor;
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
