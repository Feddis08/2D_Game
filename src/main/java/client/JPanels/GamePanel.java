package client.JPanels;

import client.blocks.Block;
import client.entities.Player;
import client.StartClient;
import client.main.Var;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel {
    public GamePanel(){
        this.setPreferredSize(new Dimension(1024,512));
    }


    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLACK);
        g2D.fillRect(0,0,1024,512);
        Boolean first_block_run = true;
        Integer blockX = 0;
        Integer blockY = 0;
        Integer index = 0;
        Player you = Var.get_player(StartClient.id);
        if (Var.blocksToRender.size() < 150 && Var.waiting_for_view_port == false){
            try {
                Var.waiting_for_view_port = true;
                StartClient.client.sendMessage("getViewport");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

            Image image = new ImageIcon("res/sprites/blocks/" + block.spriteName).getImage();
            g2D.drawImage(image, x * 64, y * 64, 64,64, null);
            index = index + 1;
        }
        index = 0;
        while (index < Var.players.size()){
            Integer x = 0;
            Integer y = 0;
            you = Var.get_player(StartClient.id);
            Player player = Var.players.get(index);
            Image image = new ImageIcon(player.spriteName).getImage();
            if (Objects.equals(player.id, StartClient.id)){
                g2D.drawImage(image, 8 * 64, 4 * 64, 64,64, null);
            }else{
                x = player.x - you.x + 8;
                y = player.y - you.y + 4;
                g2D.drawString(player.player_name, x * 64, y * 64);
                g2D.drawImage(image, x * 64, y * 64, 64,64, null);
            }
            index = index + 1;
        }
        String str = Var.blocksToRender.size() + " x:" + Var.get_player(StartClient.id).x + " y:" + Var.get_player(StartClient.id).y + " walk_time:" + Var.get_player(StartClient.id).walk_cool_down;
        g2D.drawString(str, 0, 10);
        repaint();
    }
}
