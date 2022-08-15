package server.main;

import server.socket.Client;
import server.socket.Server;

import java.io.IOException;
import java.util.Objects;

public class GameLoop extends Thread {

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
        index = 0;
        while (index < Server.clients.size()) {
            Client client = Server.clients.get(index);
            Integer index2 = 0;
            while (index2 < client.requests.size()) {
                String request = client.requests.get(index2);
                String[] command = request.split(" ");
                if (Objects.equals(command[0], "getViewport")) {
                    Tools.transfer_viewport(client);
                }
                index2 = index2 + 1;
            }
            client.requests.clear();
        }
        index = index + 1;
        }
    }