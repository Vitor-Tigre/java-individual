package Service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoSqlServerService extends ConexoesService {
    private final JdbcTemplate con;

    public ConexaoSqlServerService () {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        bds.setUrl("jdbc:sqlserver://44.216.232.183:1433;encrypt=false;databaseName=ideabd;");
        bds.setUsername("sa");
        bds.setPassword("D@nR1ck2005");

        con = new JdbcTemplate(bds);
    }

    @Override
    public JdbcTemplate getCon() {
        return this.con;
    }
}
