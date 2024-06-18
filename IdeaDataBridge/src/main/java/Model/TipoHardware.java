package Model;

public enum TipoHardware {
    PROCESSADOR("PROCESSADOR"),
    MEMORIA("MEMORIA"),
    DISCO("DISCO");

    private final String nome;

    TipoHardware(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static TipoHardware tranformarString(String tipo) {
        if (TipoHardware.PROCESSADOR.nome.equalsIgnoreCase(tipo)) {
            return TipoHardware.PROCESSADOR;
        }else if (TipoHardware.MEMORIA.nome.equalsIgnoreCase(tipo)) {
            return TipoHardware.MEMORIA;
        }else if (TipoHardware.DISCO.nome.equalsIgnoreCase(tipo)) {
            return DISCO;
        }else {
            return null;
        }
    }
}

