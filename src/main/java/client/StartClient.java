package client;

import client.JFrames.StartFrame;
import client.entities.Player;
import client.main.World;
import client.socket.Client;

import java.awt.*;
import java.io.IOException;

public class StartClient {
    public static String text = "ddd";

    public static String state = "connect to server";
    public static Integer id = 0;
    public static Player yourSelf;
    public static World world;
    public static Thread loop_thread;
    public static Client client = new Client();
    public static Client client2 = new Client();
    public static Label tfName;
    public static Label tfServerName;
    public static int max_tick_time = 50;
    public static long server_tick_start;


    public static void main(String[] args) throws IOException, InterruptedException {
        StartFrame.start();
    }
    public static void log(String log){
        System.out.println(log);
    }
}
