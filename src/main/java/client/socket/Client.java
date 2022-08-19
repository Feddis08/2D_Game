package client.socket;

import client.Start;
import client.blocks.Block;
import client.entities.Player;
import client.main.Var;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Client extends Thread{
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<String> requests = new ArrayList<>();
    private Thread th = this;

    public void startConnection(String ip, int port) throws IOException, InterruptedException {
        clientSocket = new Socket(ip, port);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sendMessage("Hallo");
        th.start();
    }
    public void run(){
        while (true){
            try {
                requests.add(listen());
            } catch (IOException e) {
                e.printStackTrace();
                th.stop();
            }
        }
    }

    public void parse_request() throws IOException {

        int index = 0;
        while (index < requests.size()){
            String str = requests.get(index);
            String[] command = str.split("!");
            if (Objects.equals(command[0], "connection")){
                System.out.println("igemdvl,f; " + str);
                if (Objects.equals(Start.state, "connect to server")){
                    if (Objects.equals(command[1], "allowed")){
                        Start.id = Integer.parseInt(command[2]);
                        Start.state = "start game";
                        sendMessage("getViewport");
                    }
                }
            }
            if (Objects.equals(command[0], "clearBlocksToRender")) {
                Var.blocksToRender.clear();
                Var.get_player(Start.id).getViewport(Start.world);
            }
            if (Objects.equals(command[0], "sendViewport")){
                Thread newThread = new Thread(() -> {
                    Start.world.blocks.clear();
                    String[] blocksStrings = str.split("#");
                    int index2 = 0;
                    while (index2 < blocksStrings.length){
                        String[] blockParams = blocksStrings[index2].split("!");
                        if (Objects.equals(blockParams[0], "sendViewport")){
                            Start.log("a");
                        }else{
                            Block block = new Block(Integer.parseInt(blockParams[0]));
                            block.type = blockParams[1];
                            block.x = Integer.parseInt(blockParams[2]);
                            block.y = Integer.parseInt(blockParams[3]);
                            block.spriteName = blockParams[4];
                            Start.world.blocks.add(block);
                        }
                        index2 = index2 + 1;
                    }
                    Var.blocksToRender.clear();
                    Var.get_player(Start.id).getViewport(Start.world);
                });
                newThread.start();
            }
            if (Objects.equals(command[0], "updatePlayer")){
                Player player = new Player(Integer.parseInt(command[1]));
                player.x = Integer.parseInt(command[2]);
                player.y = Integer.parseInt(command[3]);
                player.spriteName = command[4];
                Var.remove_player(player.id);
                Var.players.add(player);
                Var.blocksToRender.clear();
                Var.get_player(Start.id).getViewport(Start.world);
                Start.state = "start gameFrame";
            }
            requests.remove(index);
            index = index + 1;
        }

    }

    public void sendMessage(String msg) throws IOException {
        output.println(msg);
    }

    public void stopConnection() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }
    public String listen() throws IOException {
        String str = input.readLine();
        Start.log(clientSocket.getRemoteSocketAddress() + ": " +  str);
        return str;
    }
}
