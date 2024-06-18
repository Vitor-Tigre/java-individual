package Service;

public class BrilhoTelaService {
    private static String comando;
    private static final String sistemaOperacional = System.getProperty("os.name").toLowerCase();

    public static void diminuirBrilho() {
        if (sistemaOperacional.contains("win")) {
            String brilho = "1";
            telaWindows(brilho);
        } else if (sistemaOperacional.contains("nix") || sistemaOperacional.contains("nux")) {
            String brilho = "0.15";
            telaLinux(brilho);
        } else {
                throw new UnsupportedOperationException("Sistema operacional não suportado para essa ação.");
        }

    }

    public static void aumentarBrilho() {
        if (sistemaOperacional.contains("win")) {
            String brilho = "100";
            telaWindows(brilho);
        } else if (sistemaOperacional.contains("nix") || sistemaOperacional.contains("nux")) {
            String brilho = "1.0";
            telaLinux(brilho);
        } else {
            throw new UnsupportedOperationException("Sistema operacional não suportado para essa ação.");
        }
    }

    private static void telaWindows(String brilho) {
        comando = """
                    powershell (Get-WmiObject -Namespace root/WMI -Class WmiMonitorBrightnessMethods).WmiSetBrightness(1, %s)""".formatted(brilho);
        alterarBrilho();
    }

    private static void telaLinux(String brilho) {
        comando = """
                    monitor=$(xrandr | grep " connected" | cut -f1 -d " ");xrandr --output "$monitor" --brightness %s""".formatted(brilho);
        alterarBrilho();
    }

    private static void alterarBrilho() {
        try {
            Process processo = Runtime.getRuntime().exec(comando);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    if (sistemaOperacional.contains("win")) {
            comando = """
                    powershell (Get-WmiObject -Namespace root/WMI -Class WmiMonitorBrightnessMethods).WmiSetBrightness(1, 1)""";
        } else if (sistemaOperacional.contains("nix") || sistemaOperacional.contains("nux")) {
            comando = """
                    monitor=$(xrandr | grep " connected" | cut -f1 -d " ");xrandr --output "$monitor" --brightness 0.15""";
        } else {
            throw new UnsupportedOperationException("Sistema operacional não suportado para essa ação.");
        }

        try {
            Process processo = Runtime.getRuntime().exec(comando);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
     */

}
