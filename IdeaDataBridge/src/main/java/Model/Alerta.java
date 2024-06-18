package Model;

public enum Alerta {
    VERDE("VERDE"),
    AMARELO("AMARELO"),
    VERMELHO("VERMELHO");

    private final String nome;

    Alerta(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
