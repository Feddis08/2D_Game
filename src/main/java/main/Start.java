package main;

import JFrames.GameFrame;
import entities.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Start {
    public static String text = "ddd";

    public static Player yourSelf;
    public static World world;

    public static void main(String[] args) {
        world = new World();
        yourSelf = new Player(Var.playersToRender.size() + 1);
        Var.playersToRender.add(yourSelf);
        GameFrame.start();
    }
    public static void log(String log){
        System.out.println(log);
    }
}
