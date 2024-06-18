package Controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelaController {
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void iniciarTempoOcioso() {
        executor.scheduleAtFixedRate(new TarefaBrilhoController(), 0, 500, TimeUnit.MILLISECONDS);
    }
}
