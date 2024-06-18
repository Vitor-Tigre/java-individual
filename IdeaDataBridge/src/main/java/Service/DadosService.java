package Service;

import Controller.ConexoesController;
import Controller.TelaController;
import Model.HardWare;
import Model.TipoHardware;
import Model.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DadosService {
    public static void inserirDadosNoBanco(ConexaoMySqlService conMySQL, ConexaoSqlServerService conSQLServer, Totem totem) throws InterruptedException  {
        Totem totemInsertBanco = conSQLServer.getCon().queryForObject("SELECT * FROM totem WHERE hostName = ?", new BeanPropertyRowMapper<>(Totem.class),totem.getHostName());
        Integer codigoTotem = totemInsertBanco.getCodigoTotem();
        Integer idProcesador = null;
        Integer idMemoria = null;
        Integer idDisco = null;
        List<HardWare> componentes = conSQLServer.getCon().query
                ("select idHardWare, tipo from hardware where fkTotem = ? ", new BeanPropertyRowMapper<>(HardWare.class),codigoTotem);
        for (HardWare componente : componentes) {
            if (componente.getTipo().equals(TipoHardware.PROCESSADOR)) {
                idProcesador = componente.getId();
            }
            if (componente.getTipo().equals(TipoHardware.MEMORIA)) {
                idMemoria = componente.getId();
            }
            if (componente.getTipo().equals(TipoHardware.DISCO)){
                idDisco = componente.getId();
            }
        }

        TelaController.iniciarTempoOcioso();
        PriorizarProcessoService.priorizarProcesso();

        while (true) {
            System.out.println("Monitorando os componentes dessa maquina...");
            //Instanciando a data e a hora atual
            Date date = new Date();
            String dataHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            //Inserindo dados do processador;
            if (idProcesador != null) {
                conMySQL.getCon().update
                        ("insert into dadosHardWare(porcentagemUso, dataHora, nomeComponente, fkHardWare,fkTotem) values(?, ?, ?, ?, ?)",
                                totem.getProcessadorUso(), dataHora, totem.getProcessadorNome(), idProcesador, codigoTotem);
                conSQLServer.getCon().update
                        ("insert into dadosHardWare(porcentagemUso, dataHora, nomeComponente, fkHardWare,fkTotem) values(?, ?, ?, ?, ?)",
                                totem.getProcessadorUso(), dataHora, totem.getProcessadorNome(), idProcesador, codigoTotem);
                System.out.println("""
                        inserindo dados da CPU:
                        Porcentagem de uso: %d
                        Data e hora: %s
                        Nome do componente: %s
                        """.formatted(totem.getProcessadorUso(), dataHora, totem.getProcessadorNome()));
            }
            //Inserindo dados da Memoria
            if(idMemoria != null) {
                conMySQL.getCon().update
                        ("insert into dadosHardWare(porcentagemUso, dataHora, nomeComponente, fkHardWare,fkTotem) values(?, ?, ?, ?, ?)",
                                totem.getPorcentagemUsoMemoria(), dataHora, totem.getMemoriaNome(), idMemoria, codigoTotem);
                conSQLServer.getCon().update
                        ("insert into dadosHardWare(porcentagemUso, dataHora, nomeComponente, fkHardWare,fkTotem) values(?, ?, ?, ?, ?)",
                                totem.getPorcentagemUsoMemoria(), dataHora, totem.getMemoriaNome(), idMemoria, codigoTotem);
                System.out.println("""
                        inserindo dados da memoria:
                        Porcentagem de uso: %d
                        Data e hora: %s
                        Nome do componente: %s
                        """.formatted(totem.getPorcentagemUsoMemoria(), dataHora, totem.getMemoriaNome()));
            }
            //Inserindo dados do Disco
            if(idDisco != null) {
                Map<String, Long> porcentagemUsoPorVolume = totem.getPorcentagemUsoVolumes();
                System.out.println(porcentagemUsoPorVolume);
                for (Map.Entry<String, Long> entry: porcentagemUsoPorVolume.entrySet()) {
                    conMySQL.getCon().update
                            ("insert into dadosHardWare(porcentagemUso, dataHora, nomeComponente, fkHardWare,fkTotem) values(?, ?, ?, ?, ?)",
                                    entry.getValue(), dataHora, entry.getKey(),idDisco, codigoTotem);
                    conSQLServer.getCon().update
                            ("insert into dadosHardWare(porcentagemUso, dataHora, nomeComponente, fkHardWare,fkTotem) values(?, ?, ?, ?, ?)",
                                    entry.getValue(), dataHora, entry.getKey(),idDisco, codigoTotem);
                    System.out.println("""
                            inserindo dados do(s) disco(s):
                            Porcentagem de uso: %d
                            Data e hora: %s
                            Nome do componente: %s
                            """.formatted(entry.getValue(), dataHora, entry.getKey()));
                }
            }
            AlertaService.alertas(totem, conMySQL, conSQLServer);
            Thread.sleep(15000);
        }
    }
}
