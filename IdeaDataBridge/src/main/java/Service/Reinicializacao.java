package Service;

public class Reinicializacao {
    public static void reiniciar() {
        String comando;
        String sistemaOperacional = System.getProperty("os.name").toLowerCase();

        if (sistemaOperacional.contains("win")) {
            // Para Windows
            comando = "shutdown -r -t 0";
        } else if (sistemaOperacional.contains("nix") || sistemaOperacional.contains("nux")) {
            // Para sistemas baseados em Unix/Linux
            comando = "sudo reboot";
        } else {
            // Sistema operacional não suportado
            throw new UnsupportedOperationException("Sistema operacional não suportado.");
        }
        // Teste
        try {
            Process processo = Runtime.getRuntime().exec(comando);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
