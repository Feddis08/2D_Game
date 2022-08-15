package server.socket;

import server.Start;
import server.entities.Player;
import server.main.Tools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread{
    public Socket clientSocket;
    public DataInputStream input;
    public DataOutputStream output;
    public Thread th = this;
    public ArrayList<String> requests = new ArrayList<>();
    public Player player;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.input = new DataInputStream(clientSocket.getInputStream());
        this.output = new DataOutputStream(clientSocket.getOutputStream());
        Player player = new Player(Math.toIntExact(Math.round(Math.random())));
        this.player = player;
        sendMessage("connection!allowed!" + player.id);
        Tools.transfer_player(this);
        th.start();
    }
    public void run(){
        while (true){
            try {
                String str = listen();
                requests.add(str);
                Start.log("ddd " + requests.size() + " " + str);
            } catch (IOException e) {
                e.printStackTrace();
                th.stop();
            }
        }
    }
    public void sendMessage(String msg) throws IOException {
        output.writeUTF(msg);
        output.flush();
    }
    public void closeConnection() throws IOException {
        th.stop();
        clientSocket.close();
    }
    public String listen() throws IOException {
        String str = null;
            str = input.readUTF();
            Start.log(clientSocket.getRemoteSocketAddress() + ": " + str);
        return str;
    }
}
