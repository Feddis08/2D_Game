package main;

import blocks.Block;
import entities.Player;

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
    public static void player_go(Player p, String direction){
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
        }
    }

}
