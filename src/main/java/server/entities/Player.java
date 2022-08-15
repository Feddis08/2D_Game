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
    public Player(Integer id){
        this.id = id;
    }
    public ArrayList<Block> getViewport(World world){
        Integer x1 = x - 8;
        Integer y1 = y - 4;
        Integer x2 = x + 8;
        Integer y2 = y + 4;

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
    public void go(String direction){

        //Var.playerRequests.add(new PlayerRequest("go " + direction, this));

    }
}
