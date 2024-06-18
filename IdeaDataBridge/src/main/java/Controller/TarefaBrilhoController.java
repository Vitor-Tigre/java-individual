package Controller;

import Service.BrilhoTelaService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TarefaBrilhoController implements Runnable {

    private Point localMouse = new Point();
    private List<Point> posicoesMouse = new ArrayList<>();
    private Boolean brilhoBaixo = false;
    private TimerTask task;
    private Timer timer = new Timer();

    public TarefaBrilhoController() {
        posicoesMouse.add(localMouse.getLocation());
    }

    @Override
    public void run() {
        localMouse = MouseInfo.getPointerInfo().getLocation();

        if (brilhoBaixo && !posicoesMouse.get(0).equals(localMouse)) {
            System.out.println("mouse ativo.");
            BrilhoTelaService.aumentarBrilho();
            brilhoBaixo = false;
            if (task != null) {
                task.cancel();
            }

        } else if (!brilhoBaixo && posicoesMouse.get(0).equals(localMouse)) {
            System.out.println("mouse inativo.");
            brilhoBaixo = true;
            task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Brilho da tela diminuído.");
                    BrilhoTelaService.diminuirBrilho();
                }
            };
            timer.schedule(task, 15000);
        }

        posicoesMouse.clear();
        posicoesMouse.add(localMouse.getLocation());
    }
}
