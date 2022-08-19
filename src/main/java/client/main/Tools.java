package client.main;

import client.Start;
import client.blocks.Block;
import client.entities.Player;

import java.io.IOException;
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
    public static void player_go(String direction) throws IOException {


        Start.client.sendMessage("request!go " + direction);
        Start.client.sendMessage("getViewport");

    }

}
