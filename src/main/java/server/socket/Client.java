package server.socket;

import server.Start;
import server.entities.Player;
import server.main.Tools;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread{
    public Socket clientSocket;
    public BufferedReader input;
    public PrintWriter output;
    public Thread th = this;
    public ArrayList<String> requests = new ArrayList<>();
    public Player player;
    public Boolean inGame = false;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Player player = new Player(Server.clients.size());
        this.player = player;
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
        output.println(msg);
    }
    public void closeConnection() throws IOException {
        th.stop();
        clientSocket.close();
    }
    public String listen() throws IOException {
        String str = null;
            str = input.readLine();
            Start.log(clientSocket.getRemoteSocketAddress() + ": " + str);
        return str;
    }
}
