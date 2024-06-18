package Controller;

import Service.ConexaoMySqlService;
import Service.ConexaoSqlServerService;

public class CriadorController {
    public ConexaoMySqlService criarConexaoMySQL() {
        return new ConexaoMySqlService();
    }

    public ConexaoSqlServerService criarConexaoSQLServer() {
        return new ConexaoSqlServerService();
    }
}
