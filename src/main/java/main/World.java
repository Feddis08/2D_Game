package main;

import blocks.*;

import java.util.ArrayList;
import java.util.Objects;

public class World {
    public ArrayList<Block> blocks = new ArrayList<Block>();

    public World(){
        generateBlocks();
    }

    public Block get_block(Integer x, Integer y){

        Integer index = 0;
        Block result = null;
        while (index < blocks.size()){
            Block block = blocks.get(index);
            if (Objects.equals(block.x, x) && Objects.equals(block.y, y)){
                result = block;
            }
            index = index + 1;
        }
        return result;
    }

    public void generateBlocks(){
        Integer index = (-32);
        while (index < 512){
            Integer index2 = (-32);
            while (index2 < 512){
                double random = Math.floor(Math.floor(Math.random() * (4 - 1 + 1)) + 1);
                if (random == 1.0){
                    Grass_block grass_block = new Grass_block(index + index2);
                    grass_block.x = index;
                    grass_block.y = index2;
                    blocks.add(grass_block);
                }
                if (random == 2.0){
                    Stone_block grass_block = new Stone_block(index + index2);
                    grass_block.x = index;
                    grass_block.y = index2;
                    blocks.add(grass_block);
                }
                if (random == 3.0){
                    Sand_block grass_block = new Sand_block(index + index2);
                    grass_block.x = index;
                    grass_block.y = index2;
                    blocks.add(grass_block);
                }
                if (random == 4.0){
                    Water_block grass_block = new Water_block(index + index2);
                    grass_block.x = index;
                    grass_block.y = index2;
                    blocks.add(grass_block);
                }
                index2 = index2 + 1;
            }
            index = index + 1;
        }
    }
}
