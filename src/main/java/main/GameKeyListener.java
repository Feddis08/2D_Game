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
            player.go("right");
        }
        if (e.getKeyCode() == 37){
            player.go("left");
        }
        if (e.getKeyCode() == 40){
            player.go("down");
        }
        if (e.getKeyCode() == 38){
            player.go("up");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
