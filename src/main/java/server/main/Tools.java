package server.main;

import server.StartServer;
import server.blocks.Block;
import server.entities.Player;
import server.socket.Client;
import server.socket.Server;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Tools {

    public static Boolean checkCollision(Integer x1, Integer y1, Integer x2, Integer y2, Integer x3, Integer y3, Integer x4, Integer y4){
        Boolean result = false;
        if (x2 >= x3 && x1 <= x4) {
            if (y1 <= y4 && y3 <= y2) {
                result = true;
            }
        }
        return result;
    }
    public static void remove_client(Integer player_id) {
        int index = 0;
        Client client = null;
        while (index < Server.clients.size()) {
            System.out.println(Server.clients.get(index).player.id + " " + player_id + " " + Server.clients.size());
            if (Objects.equals(Server.clients.get(index).player.id, player_id)){
                System.out.println(Server.clients.get(0).player.player_name);
                Server.clients.remove(index);
                index --;
                System.out.println(Server.clients.get(0).player.player_name);
            }
            index = index + 1;
        }
    }
    public static Client get_client(Integer player_id) {
        Integer index = 0;
        Client client = null;
        while (index < Server.clients.size()) {
            if (Objects.equals(Server.clients.get(index).player.id, player_id)){
                client = Server.clients.get(index);
            }
            index = index + 1;
        }
        return client;
    }
     public static void transfer_viewport(Client client) throws IOException {
         Thread thread = new Thread("") {
             public void run(){
                 try {
                     client.viewport_transfer_client.sendMessage("clearBlocksToRender");
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 Integer index = 0;
                 String str = "sendViewport" + Server.spacing;
                 System.out.println("2");
                 while (index < client.player.getViewport(StartServer.world).size()){
                     Block block = client.player.getViewport(StartServer.world).get(index);
                     String msg = "#" + block.id + Server.spacing + block.type + Server.spacing + block.x + Server.spacing + block.y + Server.spacing + block.spriteName;
                     str = str + msg;
                     index = index + 1;
                 }
                 System.out.println("3");
                 System.out.println("run by: " + getName());
                 try {
                     client.viewport_transfer_client.sendMessage(str);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             }
         };


         thread.start();
         System.out.println("4");
     }
    public static void transfer_player(Client client) throws IOException {
        Player player = client.player;
        String msg = "updatePlayer" + Server.spacing + player.id + Server.spacing + player.x + Server.spacing + player.y + Server.spacing + player.spriteName + Server.spacing + player.player_name;
        client.sendMessage(msg);
    }
    public static void transfer_player_to_all(Client client) throws IOException {
        Player player = client.player;
        String msg = "updatePlayer" + Server.spacing + player.id + Server.spacing + player.x + Server.spacing + player.y + Server.spacing + player.spriteName + Server.spacing + player.player_name;
        Tools.sendMessageToAll(msg);
    }
    public static void sendMessageToAll(String msg) throws IOException {
        Integer index = 0;
        while (index < Server.clients.size()){
            Server.clients.get(index).sendMessage(msg);
            index = index + 1;
        }

    }


}
