package client.main;

import client.StartClient;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameKeyListener implements java.awt.event.KeyListener {
    public Integer current_Key_Code = -1;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 39){
            try {
                if (!(current_Key_Code == e.getKeyCode())){
                    Tools.player_go("right");
                    current_Key_Code = e.getKeyCode();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 37){
            try {
                if (!(current_Key_Code == e.getKeyCode())){
                    Tools.player_go("left");
                    current_Key_Code = e.getKeyCode();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 40){
            try {
                if (!(current_Key_Code == e.getKeyCode())){
                    Tools.player_go("down");
                    current_Key_Code = e.getKeyCode();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 38){
            try {
                if (!(current_Key_Code == e.getKeyCode())){
                    Tools.player_go("up");
                    current_Key_Code = e.getKeyCode();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 49){
            try {
                if (!(current_Key_Code == e.getKeyCode())){
                    Tools.player_dig();
                    current_Key_Code = e.getKeyCode();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 39){
            try {
                if (current_Key_Code == e.getKeyCode()){
                    Tools.player_go("null");
                    current_Key_Code = -1;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 37){
            try {
                if (current_Key_Code == e.getKeyCode()){
                    Tools.player_go("null");
                    current_Key_Code = -1;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 40){
            try {
                if (current_Key_Code == e.getKeyCode()){
                    Tools.player_go("null");
                    current_Key_Code = -1;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == 38){
            try {
                if (current_Key_Code == e.getKeyCode()){
                    Tools.player_go("null");
                    current_Key_Code = -1;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
