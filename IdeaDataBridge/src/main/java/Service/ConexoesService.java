package Service;

import Controller.ConexoesController;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class ConexoesService implements ConexoesController {
    @Override
    public JdbcTemplate getCon() {
        return null;
    }
}
