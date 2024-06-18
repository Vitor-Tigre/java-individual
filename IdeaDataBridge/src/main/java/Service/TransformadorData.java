package Service;

import java.sql.Date;

public class TransformadorData {
    public static Date transformar(Integer dia, Integer mes, Integer ano) {
        return new Date(ano,mes,dia);
    }
}
