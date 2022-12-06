package client.socket;

import client.JFrames.StartFrame;
import client.StartClient;
import client.blocks.Block;
import client.entities.Player;
import client.main.Var;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Client extends Thread{
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<String> requests = new ArrayList<>();
    private Thread th = this;
    public String spacing = "";
    public Boolean is_main_client = true;
    public String name = "main";

    public void startConnection(String ip, int port) throws IOException, InterruptedException {
        clientSocket = new Socket(ip, port);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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

    public void parse_request() throws IOException, InterruptedException {

        int index = 0;
        while (index < requests.size()){
            if (requests.get(index).split("SPACING").length > 1 ){
                spacing = requests.get(index).split("SPACING")[1];
            }
            String str = requests.get(index);
            String[] command = str.split(spacing);
            StartClient.log(command[0]);
            if (Objects.equals(command[0], "serverInfo")){
                StartClient.log("ddd");
                StartFrame.label1.setText("online: " + command[3]);
                StartFrame.label2.setText("");
                StartFrame.label3.setText("server name: " + command[2]);
                StartFrame.label4.setText("status: " + command[1]);
            }
            if (Objects.equals(command[0], "connection")){
                if (Objects.equals(command[1], "allowed")){
                    if (Objects.equals(name, "main")){
                        StartClient.id = Integer.parseInt(command[2]);
                        StartClient.state = "start game";
                        String[] str2 = StartFrame.tfServerName.getText().split(":");
                        StartClient.client2 = new Client();
                        StartClient.client2.is_main_client = false;
                        StartClient.client2.name = "viewport";
                        StartClient.client2.startConnection(str2[0], Integer.parseInt(str2[1]));
                        StartClient.client2 .sendMessage("join as viewport" + spacing + StartClient.id);
                        System.out.println("fesfksfksafkoaseoif " + name);
                    }
                    if (Objects.equals(name, "viewport")){
                        sendMessage("go" + spacing + "up");
                    }
                }
            }
            if (Objects.equals(command[0], "clearBlocksToRender")) {
                Var.blocksToRender.clear();
                Var.get_player(StartClient.id).getViewport(StartClient.world);
            }
            if (Objects.equals(command[0], "sendViewport")){
                Thread newThread = new Thread(() -> {
                    StartClient.world.blocks.clear();
                    String[] blocksStrings = str.split("#");
                    int index2 = 0;
                    while (index2 < blocksStrings.length){
                        String[] blockParams = blocksStrings[index2].split(spacing);
                        if (Objects.equals(blockParams[0], "sendViewport")){
                            StartClient.log("a");
                        }else{
                            Block block = new Block(Integer.parseInt(blockParams[0]));
                            block.type = blockParams[1];
                            block.x = Integer.parseInt(blockParams[2]);
                            block.y = Integer.parseInt(blockParams[3]);
                            block.spriteName = blockParams[4];
                            StartClient.world.blocks.add(block);
                        }
                        index2 = index2 + 1;
                    }
                    Var.blocksToRender.clear();
                    Var.get_player(StartClient.id).getViewport(StartClient.world);
                });
                newThread.start();
            }
            if (Objects.equals(command[0], "updatePlayer")){
                Player player = new Player(Integer.parseInt(command[1]));
                player.x = Integer.parseInt(command[2]);
                player.y = Integer.parseInt(command[3]);
                player.spriteName = command[4];
                player.player_name = command[5];
                Var.remove_player(player.id);
                Var.players.add(player);
                Var.blocksToRender.clear();
                Var.get_player(StartClient.id).getViewport(StartClient.world);
                StartClient.state = "start gameFrame";
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
        System.out.println(str);
        System.out.println("sdadsaasdadasda " + name);
        return str;
    }
}
