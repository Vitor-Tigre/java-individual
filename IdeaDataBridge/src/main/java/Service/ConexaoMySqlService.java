package Service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMySqlService extends ConexoesService {
    private final JdbcTemplate con;

    public ConexaoMySqlService() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/ideabd");
        bds.setUsername("root");
        bds.setPassword("Sptech123");

        con = new JdbcTemplate(bds);
    }

    @Override
    public JdbcTemplate getCon() {
        return this.con;
    }
}
