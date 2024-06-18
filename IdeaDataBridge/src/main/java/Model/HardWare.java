package Model;

public class HardWare {
    private Integer idHardWare;
    private TipoHardware tipo;

    public HardWare(Integer id, TipoHardware tipo) {
        this.idHardWare = id;
        this.tipo = tipo;
    }

    public HardWare(Integer id, String tipo) {
        this.idHardWare = id;
        this.tipo = TipoHardware.tranformarString(tipo);
    }

    public HardWare() {
    }

    public Integer getId() {
        return idHardWare;
    }

    public void setIdHardWare(Integer id) {
        this.idHardWare = id;
    }

    public TipoHardware getTipo() {
        return tipo;
    }

    public void setTipo(TipoHardware tipo) {
        this.tipo = tipo;
    }


    @Override
    public String toString() {
        return """
                
                ID do hardware: %d
                tipo: %s
                """.formatted(this.idHardWare, this.tipo);
    }
}
