package server.main;

import server.StartServer;
import server.socket.Client;
import server.socket.Server;

import java.io.IOException;
import java.util.Objects;
import java.util.SplittableRandom;

public class GameLoop extends Thread {

    public void run(){
        while (true){
            try {
                loop();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(0);
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
                Tools.player_go(playerRequest.client, request[1]);
            }
            index = index + 1;
        }
        Var.playerRequests.clear();
        index = 0;
        String spacing = Server.spacing;
        while (index < Server.clients.size()) {
            Client client = Server.clients.get(index);
            Integer index2 = 0;
            while (index2 < client.requests.size()) {
                String request = client.requests.get(index2);
                String[] command = request.split(spacing);
                StartServer.log("dad" + request + " " + index);
                if (Objects.equals(command[0], "getViewport")) {
                    Tools.transfer_viewport(client);
                }
                if (Objects.equals(command[0], "request")) {
                    PlayerRequest playerRequest = new PlayerRequest(command[1], client);
                    Var.playerRequests.add(playerRequest);
                }
                if (Objects.equals(command[0], "join")) {
                    System.out.println(client.player.player_name + "aaa");
                    client.sendMessage("connection"+ spacing +"allowed" + spacing + client.player.id + spacing + client.player.player_name);
                    client.inGame = true;
                    client.player.player_name = command[1];
                    Tools.transfer_player(client);
                }
                if (Objects.equals(command[0], "getServerInfo")) {
                    client.sendMessage("serverInfo" + spacing + StartServer.status + spacing + StartServer.server_name + spacing + Server.clients.size());
                }
                index2 = index2 + 1;
            }
            client.requests.clear();
            index = index + 1;
        }
        }
    }