package server.main;

import client.StartClient;
import server.StartServer;
import server.socket.Client;
import server.socket.Server;

import java.io.IOException;
import java.util.Objects;
import java.util.SplittableRandom;

public class GameLoop extends Thread {
    public static Boolean sync_next_tick = false;
    public static int tick_count = 0;
    public void run(){
        while (true){
            try {
                loop();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loop() throws IOException, InterruptedException {
        long currentTime = System.currentTimeMillis();
        Integer index = 0;
        String spacing = Server.spacing;
        while (index < Server.clients.size()) {
            Client client = Server.clients.get(index);
            Integer index2 = 0;
            if (client.inGame) client.player.doTick();
            if (tick_count % 20 == 0){
                //Tools.send_tick_time_to_all(currentTime);
            }
            while (index2 < client.requests.size()) {
                System.out.println(client.requests.get(index2));
                String request = client.requests.get(index2);
                String[] command = request.split(spacing);
                System.out.println(request + client.player.id);
                if (Objects.equals(command[0], "getViewport")) {
                    client.player.async_update_player_viewport_location_and_sprite();
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
                }
                if (Objects.equals(command[0], "get spacing")) {
                    client.sendMessage("send spacing" + spacing);
                }
                if (Objects.equals(command[0], "join")) {
                    client.sendMessage("connection"+ spacing +"allowed" + spacing + client.player.id + spacing + client.player.player_name);
                    client.inGame = true;
                    client.player.player_name = command[1];
                    Tools.transfer_player(client);
                    Tools.send_tick_time_to_all(currentTime);
                }
                if (Objects.equals(command[0], "getServerInfo")) {
                    client.sendMessage("serverInfo" + spacing + StartServer.status + spacing + StartServer.server_name + spacing + Server.clients.size() + spacing + currentTime);
                }
                index2 = index2 + 1;
            }
            client.requests.clear();
            index = index + 1;
        }
        tick_count += 1;
        if (sync_next_tick) Tools.send_tick_time_to_all(currentTime); sync_next_tick = false;
        long currentTime2 = System.currentTimeMillis();
        int diff = (int) (currentTime2 - currentTime);
        if (diff >= StartServer.max_tick_time){
            StartServer.log("WARNING: Server took " + diff + "ms for one tick!");
            sync_next_tick = true;
        }else{
            sleep(StartServer.max_tick_time - diff);
        }
        }
    }