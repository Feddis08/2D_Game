package main;

import blocks.Block;
import blocks.Grass_block;
import blocks.Sand_block;
import blocks.Stone_block;

import java.util.ArrayList;

public class World {
    public ArrayList<Block> blocks = new ArrayList<Block>();

    public World(){
        generateBlocks();
    }

    public void generateBlocks(){
        Integer index = (-32);
        while (index < 128){
            Integer index2 = (-32);
            while (index2 < 128){
                double random = Math.floor(Math.floor(Math.random() * (3 - 1 + 1)) + 1);
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
                index2 = index2 + 1;
            }
            index = index + 1;
        }
    }
}
