package client.socket;

import client.Start;
import client.blocks.Block;
import client.main.Var;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Client extends Thread{
    private Socket clientSocket;
    private DataOutputStream output;
    private DataInputStream input;
    private Thread th = this;

    public void startConnection(String ip, int port) throws IOException, InterruptedException {
        clientSocket = new Socket(ip, port);
        output = new DataOutputStream(clientSocket.getOutputStream());
        input = new DataInputStream(clientSocket.getInputStream());

        sendMessage("Hallo");
        th.start();
    }
    public void run(){
        while (true){
            try {
                String str = listen();
                String[] command = str.split("!");
                if (Objects.equals(command[0], "connection")){
                    if (Objects.equals(Start.state, "connect to server")){
                        if (Objects.equals(command[1], "allowed")){
                            Start.state = "start game";
                            sendMessage("getViewport");
                        }
                    }
                }
                if (Objects.equals(command[0], "clearBlocksToRender")) {
                    Var.blocksToRender.clear();
                }
                if (Objects.equals(command[0], "sendViewport")){
                    Block block = new Block(Integer.parseInt(command[1]));
                    block.type = command[2];
                    block.x = Integer.parseInt(command[3]);
                    block.y = Integer.parseInt(command[4]);
                    block.spriteName = command[5];
                    Var.blocksToRender.add(block);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(String msg) throws IOException {
        output.writeUTF(msg);
        output.flush();
    }

    public void stopConnection() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }
    public String listen() throws IOException {
        String str =  input.readUTF();
        Start.log(clientSocket.getRemoteSocketAddress() + ": " +  str);
        return str;
    }
}
