package client.main;

import client.JFrames.GameFrame;
import client.StartClient;
import client.entities.Player;
import server.StartServer;

import java.io.IOException;
import java.util.Objects;

public class GameLoop extends Thread {

    public static Boolean gameFrameOn = false;
    public static Boolean do_tick = false;
    public static Boolean sync = false;

    public void run(){
        while (true){
            try {
                loop();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void loop() throws IOException, InterruptedException {
        long currentTime = System.currentTimeMillis();
        StartClient.client.parse_request();
        StartClient.client2.parse_request();
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
        if (do_tick){
            index = 0;
            while (index < Var.players.size()){
                Player player = Var.players.get(index);
                player.doTick();
                index = index + 1;
            }
        }
        if (Objects.equals(StartClient.state, "start gameFrame")){
            if (!(gameFrameOn))
                GameFrame.start();
            gameFrameOn = true;
        }
        if (sync){
            sync = false;
            sleep(Tools.get_tick_sleep(currentTime, StartClient.server_tick_start, StartClient.max_tick_time));
            StartClient.log(Tools.get_tick_sleep(currentTime, StartClient.server_tick_start, StartClient.max_tick_time) + "ms");
        }else{
            long currentTime2 = System.currentTimeMillis();
            int diff = (int) (currentTime2 - currentTime);
            if (diff >= StartServer.max_tick_time){
                StartServer.log("WARNING: Client took " + diff + "ms for one tick!");
                sleep(Tools.get_tick_sleep(currentTime, StartClient.server_tick_start, StartClient.max_tick_time));
                StartClient.log(Tools.get_tick_sleep(currentTime, StartClient.server_tick_start, StartClient.max_tick_time) + "ms");
            }else{
                if (!sync) {sleep(StartClient.max_tick_time - diff); }
            }
        }
    }

}
