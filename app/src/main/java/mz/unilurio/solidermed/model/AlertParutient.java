package mz.unilurio.solidermed.model;

public class AlertParutient {

        private  int segundos;
        private  int idParturiente;


    public AlertParutient(int segundos, int idParturiente) {
        this.segundos = segundos;
        this.idParturiente = idParturiente;
    }

    public int getSegundos() {
        this.segundos=--segundos;
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getIdParturiente() {
        return idParturiente;
    }

    public void setIdParturiente(int idParturiente) {
        this.idParturiente = idParturiente;
    }
}
