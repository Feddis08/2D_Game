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
        Player player = Start.yourSelf;
        if (e.getKeyCode() == 39){
            try {
                Start.client.sendMessage("request!go right");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 37){
            try {
                Start.client.sendMessage("request!go left");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 40){
            try {
                Start.client.sendMessage("request!go down");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 38){
            try {
                Start.client.sendMessage("request!go up");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
