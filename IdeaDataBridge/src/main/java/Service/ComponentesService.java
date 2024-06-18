package Service;

import Controller.ConexoesController;
import Model.HardWare;
import Model.TipoHardware;
import Model.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class ComponentesService {
    public static void inserirComponentesNoBD(ConexaoMySqlService conMySQL, ConexaoSqlServerService conSQLServer, Totem totem ) {
        List<HardWare> hardwares = conSQLServer.getCon().query("select idHardWare, tipo from hardware where fkTotem = ?", new BeanPropertyRowMapper<>(HardWare.class),totem.getCodigoTotem());

        if (hardwares.isEmpty()) {
            Integer codigoTotem = totem.getCodigoTotem();
            System.out.println("Inserindo informações dos componentes dessa maquina no banco de dados!");
            if (totem.getProcessador() != null) {
                conMySQL.getCon().update("insert into hardware(tipo,fkTotem) values(?,?)", TipoHardware.PROCESSADOR.getNome(),codigoTotem);
                conSQLServer.getCon().update("insert into hardware(tipo,fkTotem) values(?,?)", TipoHardware.PROCESSADOR.getNome(),codigoTotem);
            }
            if (totem.getMemoria() != null) {
                conMySQL.getCon().update("insert into hardware(tipo,fkTotem) values(?,?)", TipoHardware.MEMORIA.getNome(),codigoTotem);
                conSQLServer.getCon().update("insert into hardware(tipo,fkTotem) values(?,?)", TipoHardware.MEMORIA.getNome(),codigoTotem);
            }
            if (totem.getGrupoDisco() != null) {
                conMySQL.getCon().update("insert into hardware(tipo,fkTotem) values(?,?)", TipoHardware.DISCO.getNome(),codigoTotem);
                conSQLServer.getCon().update("insert into hardware(tipo,fkTotem) values(?,?)", TipoHardware.DISCO.getNome(),codigoTotem);
            }
            try {
                DadosService.inserirDadosNoBanco(conMySQL, conSQLServer, totem);
            }catch (Exception e) {
                System.out.println("Houve um erro durante o processo de monitoramento!");
                System.out.println(e);
            }
        }else {
            try {
                DadosService.inserirDadosNoBanco(conMySQL, conSQLServer, totem);
            }catch (Exception e) {
                System.out.println("Houve um erro durante o processo de monitoramento!");
                System.out.println(e);
            }
        }

    }
}
