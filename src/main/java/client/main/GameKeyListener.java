package client.main;

import client.Start;
import client.entities.Player;
import server.socket.Client;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameKeyListener implements java.awt.event.KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Start.log(String.valueOf(e.getKeyCode()));
        if (e.getKeyCode() == 39){
            try {
                Tools.player_go("right");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 37){
            try {
                Tools.player_go("left");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 40){
            try {
                Tools.player_go("down");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 38){
            try {
                Tools.player_go("up");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
