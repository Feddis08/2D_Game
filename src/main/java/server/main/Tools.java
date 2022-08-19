package server.main;

import server.Start;
import server.blocks.Block;
import server.entities.Player;
import server.socket.Client;
import server.socket.Server;

import java.io.IOException;
import java.util.ArrayList;
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
    public static void player_go(Client client, String direction) throws IOException {
        Thread newThread = new Thread(() -> {
            try {
                Player p = client.player;
                Integer x = p.x;
                Integer y = p.y;
                String spriteName = "";
                if (Objects.equals(direction, "right")){
                    x = p.x + 1;
                    spriteName = "res/sprites/entities/player/player4.png";
                }
                if (Objects.equals(direction, "left")){
                    x = p.x - 1;
                    spriteName = "res/sprites/entities/player/player3.png";
                }
                if (Objects.equals(direction, "down")){
                    y = p.y + 1;
                    spriteName = "res/sprites/entities/player/player1.png";
                }
                if (Objects.equals(direction, "up")){
                    y = p.y - 1;
                    spriteName = "res/sprites/entities/player/player2.png";
                }
                Block block = Start.world.get_block(x, y);
                if (!(block == null)){
                    p.x = x;
                    p.y = y;
                    p.spriteName = spriteName;
                    if (Objects.equals(block.type, "WATER_BLOCK")){
                        p.spriteName = "res/sprites/entities/player/player5.png";
                    }
                    Thread.sleep(500);
                    transfer_player_to_all(client);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        newThread.start();
    }
     public static void transfer_viewport(Client client) throws IOException {
         Thread newThread = new Thread(() -> {
             try {
                 client.sendMessage("clearBlocksToRender");
                 Integer index = 0;
                 String str = "sendViewport!";
                 while (index < client.player.getViewport(Start.world).size()){
                     Block block = client.player.getViewport(Start.world).get(index);
                     String msg = "#" + block.id + "!" + block.type + "!" + block.x + "!" + block.y + "!" + block.spriteName;
                     str = str + msg;
                     index = index + 1;
                 }
                 Start.log(str);
                 client.sendMessage(str);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         });
         newThread.start();
    }
    public static void transfer_player(Client client) throws IOException {
        Player player = client.player;
        String msg = "updatePlayer!" + player.id + "!" + player.x + "!" + player.y + "!" + player.spriteName + "!" + player.player_name;
        client.sendMessage(msg);
    }
    public static void transfer_player_to_all(Client client) throws IOException {
        Player player = client.player;
        String msg = "updatePlayer!" + player.id + "!" + player.x + "!" + player.y + "!" + player.spriteName + "!" + player.player_name;
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
