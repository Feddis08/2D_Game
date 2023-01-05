package server.entities;

import server.StartServer;
import server.blocks.Block;
import server.main.Tools;
import server.main.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player {
    public Integer id = 0;
    public static String spriteName = "res/sprites/entities/player/player1.png";
    public static Integer x = 32;
    public static Integer y = 32;
    public Integer walk_speed = 10;
    public Integer dig_speed = 500;
    public Integer walk_cool_down = 0;
    public String move_direction = "null";
    public String player_name = "";
    public Integer up_time = 0;
    public Player(Integer id){
        this.id = id;
    }
    public Thread block_transfer_thread = new Thread(String.valueOf(this.id)){
        public void run (){
            try {
                Tools.transfer_viewport(Tools.get_client(Integer.parseInt(this.getName())));
                this.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };
    public void dig(){

    }
    public Block get_facing_block(){
        Block block = null;
        if (Objects.equals(this.move_direction, "up"))
            StartServer.world.get_block(this.x, this.y ++);
        if (Objects.equals(this.move_direction, "up"))
            StartServer.world.get_block(this.x, this.y ++);

            return block;
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
        Block block = StartServer.world.get_block(wantedX, wantedY);
        Boolean move = true;
        if (!(block == null)) {
            if (Objects.equals(block.type, "WATER_BLOCK")) {
                spriteName = "res/sprites/entities/player/player5.png";
            }
        }
        if (move){
            x = wantedX;
            y = wantedY;
            Tools.transfer_player_to_all(Tools.get_client(id));
        }
        //this.async_update_player_viewport_location_and_sprite();
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
    public void async_update_player_viewport_location_and_sprite() throws IOException {
        Tools.transfer_player_to_all(Tools.get_client(id));
        if (!(Tools.get_client(id).viewport_transfer_client == null)){
            this.block_transfer_thread.stop();
            this.block_transfer_thread.run();
        }
    }
}
