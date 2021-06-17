package mz.unilurio.solidermed.model;

public class Parturiente {
    private String name;
    private String surname;
    private int idade;
    private double dilatation;
    private boolean isTransfered;
    private String reason;

    public Parturiente(){}

    public Parturiente(String name, String surname, int idade, double dilatation, boolean isTransfered, String reason) {
        this.name = name;
        this.surname = surname;
        this.idade = idade;
        this.dilatation = dilatation;
        this.isTransfered = isTransfered;
        this.reason = reason;
    }


}
