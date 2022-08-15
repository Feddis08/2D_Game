package server;

import server.main.GameLoop;
import server.main.World;
import server.socket.Server;

import java.io.IOException;

public class Start {
    public static World world;
    public static Thread loop_thread;

    public static void main(String[] args) throws IOException, InterruptedException {
        loop_thread = new GameLoop();
        loop_thread.start();
        world = new World();
        Server.th.start();
    }

    public static void log(String log){
                                     System.out.println(log);
                                                             }
}
