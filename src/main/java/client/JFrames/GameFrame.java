package client.JFrames;

import client.JPanels.GamePanel;
import client.main.GameKeyListener;

import javax.swing.*;
public class GameFrame {
    public static GamePanel panel;
    public static void start(){
        panel = new GamePanel();
        JFrame frame = new JFrame();
        frame.setTitle("J2D_Game - GAME");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.addKeyListener(new GameKeyListener());
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
