package client.JPanels;

import client.blocks.Block;
import client.entities.Player;
import client.Start;
import client.main.Var;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel(){
        this.setPreferredSize(new Dimension(1024,512));
    }


    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLACK);
        Boolean first_block_run = true;
        Integer blockX = 0;
        Integer blockY = 0;
        Integer index = 0;
        //Start.yourSelf.getViewport(Start.world);
        while (index < Var.blocksToRender.size()){
            Block block = Var.blocksToRender.get(index);
            if (first_block_run){
                blockX = block.x;
                blockY = block.y;
                first_block_run = false;
            }else{
                if (block.x <= blockX){
                    blockX = block.x;
                }
                if (block.y <= blockY){
                    blockY = block.y;
                }
            }
            index = index + 1;
        }
        index = 0;
        while (index < Var.blocksToRender.size()){
            Block block = Var.blocksToRender.get(index);
            Integer x = Math.abs(blockX - block.x);
            Integer y = Math.abs(blockY - block.y);

            Image image = new ImageIcon(block.spriteName).getImage();
            g2D.drawImage(image, x * 64, y * 64, 64,64, null);
            index = index + 1;
        }
        index = 0;
        while (index < Var.playersToRender.size()){
            Player player = Var.playersToRender.get(index);
            Image image = new ImageIcon(player.spriteName).getImage();
            g2D.drawImage(image, 8 * 64, 4 * 64, 64,64, null);
            index = index + 1;
        }
        String str = Var.blocksToRender.size() + " x:" + Start.yourSelf.x + " y:" + Start.yourSelf.y;
        g2D.drawString(str, 0, 10);
        repaint();
    }
}
