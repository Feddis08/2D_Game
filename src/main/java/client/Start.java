package client;

import client.JFrames.GameFrame;
import client.entities.Player;
import client.main.GameLoop;
import client.main.Var;
import client.main.World;
import client.socket.Client;

import java.io.IOException;

public class Start {
    public static String text = "ddd";

    public static String state = "connect to server";
    public static Integer id = 0;
    public static Player yourSelf;
    public static World world;
    public static Thread loop_thread;
    public static Client client = new Client();


    public static void main(String[] args) throws IOException, InterruptedException {
        world = new World();
        client.startConnection("localhost", 3333);
        loop_thread = new GameLoop();
        loop_thread.start();
    }
    public static void log(String log){
        System.out.println(log);
    }
}
