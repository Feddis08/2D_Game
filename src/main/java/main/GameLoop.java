package main;

import entities.Player;

import javax.management.StandardEmitterMBean;
import java.util.Objects;

public class GameLoop extends Thread {

    public void run(){
        while (true){
            loop();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loop(){
        Integer index = 0;
        while (index < Var.playerRequests.size()){
            PlayerRequest playerRequest = Var.playerRequests.get(index);
            String[] request = playerRequest.type.split(" ");
            if (Objects.equals(request[0], "go")){
                Tools.player_go(playerRequest.player, request[1]);
            }
            index = index + 1;
        }
        Var.playerRequests.clear();
    }

}
