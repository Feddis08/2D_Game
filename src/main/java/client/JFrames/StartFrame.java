package client.JFrames;

import client.JPanels.GamePanel;
import client.Start;
import client.main.GameKeyListener;
import client.main.GameLoop;
import client.main.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartFrame extends Canvas{


    public static JFrame frame = new JFrame();
    public static JTextField tfName = new JTextField("player_name");
    public static JTextField tfServerName = new JTextField("localhost:5068");
    public static JButton b1 = new JButton();
    public static JButton b2 = new JButton();
    public static JLabel label1 = new JLabel();
    public static JLabel label2 = new JLabel();
    public static JLabel label3 = new JLabel();
    public static JLabel label4 = new JLabel();
    public static Boolean b1_on = false;

    public static void start(){
        frame.setTitle("J2D_Game - START");
        frame.setSize(256, 256);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
        frame.setIconImage(icon);

        tfName.setForeground(Color.BLUE);
        tfName.setBackground(Color.YELLOW);
        tfName.setSize(256, 32);
        tfName.setLocation(0,0);
        tfName.setVisible(true);
        frame.add(tfName);

        tfServerName.setForeground(Color.BLUE);
        tfServerName.setBackground(Color.YELLOW);
        tfServerName.setSize(256, 32);
        tfServerName.setLocation(0,32);
        tfServerName.setVisible(true);
        frame.add(tfServerName);


        b1.setText("connect");
        b1.setSize(128, 32);
        b1.setBackground(Color.GREEN);
        b1.setLocation(0, 64);
        b1.setVisible(true);
        frame.add(b1);

        b2.setText("join");
        b2.setSize(128, 32);
        b2.setBackground(Color.GREEN);
        b2.setLocation(128, 64);
        b2.setVisible(true);
        frame.add(b2);

        label1.setSize(128, 32);
        label1.setLocation(0, 96);
        label1.setText("online: ");
        label1.setVisible(true);
        frame.add(label1);

        label2.setSize(128, 32);
        label2.setLocation(128, 96);
        label2.setText("server name");
        label2.setVisible(true);
        frame.add(label2);

        label3.setSize(128, 32);
        label3.setLocation(0, 128);
        label3.setText("ping: ");
        label3.setVisible(true);
        frame.add(label3);


        label4.setSize(128, 32);
        label4.setLocation(128, 128);
        label4.setText("status: ");
        label4.setVisible(true);
        frame.add(label4);

        frame.setLayout(null);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(b1_on)){
                    b1_on = true;
                    Start.log("b1");
                    try {
                        String[] str = tfServerName.getText().split(":");
                        Start.loop_thread = new GameLoop();
                        Start.loop_thread.start();
                        Start.client.startConnection(str[0], Integer.parseInt(str[1]));
                        Start.client.sendMessage("getServerInfo");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b1_on) {
                    Start.log("b2");
                    Start.world = new World();
                    try {
                        Start.client.sendMessage("join!" + tfName.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        frame.setVisible(true);
    }
}
