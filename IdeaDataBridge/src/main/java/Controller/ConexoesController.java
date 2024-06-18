package Controller;

import org.springframework.jdbc.core.JdbcTemplate;

public interface ConexoesController {
    public JdbcTemplate getCon();
}
