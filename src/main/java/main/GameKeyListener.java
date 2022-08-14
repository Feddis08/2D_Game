package main;

import entities.Player;

import java.awt.event.KeyEvent;

public class GameKeyListener implements java.awt.event.KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = Start.yourSelf;
        if (e.getKeyCode() == 39){
            player.x = player.x + 1;
            player.spriteName = "res/sprites/entities/player/player4.png";
        }
        if (e.getKeyCode() == 37){
            player.x = player.x - 1;
            player.spriteName = "res/sprites/entities/player/player3.png";
        }
        if (e.getKeyCode() == 40){
            player.y = player.y + 1;
            player.spriteName = "res/sprites/entities/player/player1.png";
        }
        if (e.getKeyCode() == 38){
            player.y = player.y - 1;
            player.spriteName = "res/sprites/entities/player/player2.png";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
