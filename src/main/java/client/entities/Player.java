package client.entities;

import client.StartClient;
import client.blocks.Block;
import client.main.*;

import java.io.IOException;
import java.util.Objects;

public class Player {
    public Integer id = 0;
    public String spriteName = "res/sprites/entities/player/player1.png";
    public Integer x = 32;
    public Integer y = 32;
    public Integer walk_speed = 25;
    public Integer dig_speed = 500;
    public Integer walk_cool_down = 0;
    public String move_direction = "null";
    public String player_name = "";
    public int up_time;

    public Player(Integer id){
        this.id = id;
    }
    public void getViewport(World world){
        Integer x1 = x - 8;
        Integer y1 = y - 4;
        Integer x2 = x + 8;
        Integer y2 = y + 4;

        Integer index = 0;
        Var.blocksToRender.clear();
        while (index < world.blocks.size()){
            Block block = world.blocks.get(index);
            if (Tools.checkCollision(x1, y1, x2, y2, block.x, block.y, block.x, block.y)){
                Var.blocksToRender.add(block);
            }
            index = index + 1;
        }

    }
    public void doTick() throws IOException {
        update_times();
        move(this.move_direction);
    }
    public void update_times(){
        this.up_time ++;
        update_walk_cool_down();
    }
    public void update_walk_cool_down(){
        if (this.walk_cool_down != 0){
            this.walk_cool_down --;
        }
    }
    public void check_move(Integer wantedX, Integer wantedY) throws IOException {
        Block block = StartClient.world.get_block(wantedX, wantedY);
        Boolean move = true;
        if (!(block == null)) {
            if (Objects.equals(block.type, "WATER_BLOCK")) {
                spriteName = "res/sprites/entities/player/player5.png";
            }
        }
        if (move){
            x = wantedX;
            y = wantedY;
            getViewport(StartClient.world);
        }
    }
    public void change_move_direction(String move_direction){
        this.move_direction = move_direction;
    }
    public void move(String direction) throws IOException {
        if (walk_cool_down == 0){
            if (!Objects.equals(direction, "null")){
                this.walk_cool_down = this.walk_speed;
                Boolean changed = false;
                Integer preX = x;
                Integer preY = y;
                if (Objects.equals(direction, "right")){
                    changed = true;
                    preX ++;
                    spriteName = "res/sprites/entities/player/player4.png";
                }
                if (Objects.equals(direction, "left")){
                    changed = true;
                    preX --;
                    spriteName = "res/sprites/entities/player/player3.png";
                }
                if (Objects.equals(direction, "down")){
                    changed = true;
                    preY ++;
                    spriteName = "res/sprites/entities/player/player1.png";
                }
                if (Objects.equals(direction, "up")){
                    changed = true;
                    preY --;
                    spriteName = "res/sprites/entities/player/player2.png";
                }
                if (changed) this.check_move(preX, preY);
            }
        }
    }
    public void go(String direction){

        Var.playerRequests.add(new PlayerRequest("go " + direction, this));

    }
}
