package client.main;

import client.JFrames.GameFrame;
import client.Start;

import java.io.IOException;
import java.util.Objects;

public class GameLoop extends Thread {

    public static Boolean gameFrameOn = false;

    public void run(){
        while (true){
            try {
                loop();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loop() throws IOException {
        Start.client.parse_request();
        Integer index = 0;
        while (index < Var.playerRequests.size()){
            PlayerRequest playerRequest = Var.playerRequests.get(index);
            String[] request = playerRequest.type.split(" ");
            if (Objects.equals(request[0], "go")){
                //Tools.player_go(playerRequest.player, request[1]);
            }
            index = index + 1;
        }
        Var.playerRequests.clear();

        if (Objects.equals(Start.state, "start gameFrame")){
            if (!(gameFrameOn))
                GameFrame.start();
            gameFrameOn = true;
        }
    }

}
