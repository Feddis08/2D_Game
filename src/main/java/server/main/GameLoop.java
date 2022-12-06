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
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loop() throws IOException {
        Integer index = 0;
        String spacing = Server.spacing;
        while (index < Server.clients.size()) {
            Client client = Server.clients.get(index);
            Integer index2 = 0;
            if (client.inGame) client.player.doTick();
            while (index2 < client.requests.size()) {
                System.out.println(client.requests.get(index2));
                String request = client.requests.get(index2);
                String[] command = request.split(spacing);
                System.out.println(request + client.player.id);
                if (Objects.equals(command[0], "getViewport")) {
                    Tools.transfer_viewport(client);
                }
                if (Objects.equals(command[0], "join as viewport")) {
                    Client client1 = Tools.get_client(Integer.parseInt(command[1]));
                    //if (client1.clientSocket.getInetAddress().getAddress() == client.clientSocket.getInetAddress().getAddress()){
                        client.is_main_client = false;
                        client.player.id = -1;
                        Tools.remove_client(-1);
                        client1.viewport_transfer_client = client;
                        System.out.println(client.player.id);
                        System.out.println(client1.player.id);

                    client.sendMessage("connection"+ spacing +"allowed");
                    //}
                }
                if (Objects.equals(command[0], "go")) {
                    client.player.change_move_direction(command[1]);
                    System.out.println("adddada");
                }
                if (Objects.equals(command[0], "get spacing")) {
                    client.sendMessage("send spacing" + spacing);
                }
                if (Objects.equals(command[0], "join")) {
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