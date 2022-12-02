package server.entities;

import server.blocks.Block;
import server.main.Tools;
import server.main.World;

import java.util.ArrayList;

public class Player {
    public Integer id = 0;
    public String spriteName = "res/sprites/entities/player/player1.png";
    public Integer x = 32;
    public Integer y = 32;
    public Integer walk_speed = 3000;
    public Integer walk_cool_down = 0;
    public String player_name = "";
    public Player(Integer id){
        this.id = id;
    }
    public ArrayList<Block> getViewport(World world){
        Integer x1 = x - 10;
        Integer y1 = y - 6;
        Integer x2 = x + 10;
        Integer y2 = y + 6;

        Integer index = 0;
        ArrayList<Block> blocks = new ArrayList<>();
        while (index < world.blocks.size()){
            Block block = world.blocks.get(index);
            if (Tools.checkCollision(x1, y1, x2, y2, block.x, block.y, block.x, block.y)){
                blocks.add(block);
            }
            index = index + 1;
        }
        return blocks;
    }
}
