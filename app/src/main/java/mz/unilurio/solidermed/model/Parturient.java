package mz.unilurio.solidermed.model;

public class Parturient {
    private String name;
    private String surname;
    private int idade;
    private boolean isTransfered;
    private String reason;

    public Parturient(){}

    public Parturient(String name, String surname, int idade) {
        this.name = name;
        this.surname = surname;
        this.idade = idade;
    }

    public Parturient(String name, String surname, int idade, boolean isTransfered, String reason) {
        this.name = name;
        this.surname = surname;
        this.idade = idade;
        this.isTransfered = isTransfered;
        this.reason = reason;
    }


}
