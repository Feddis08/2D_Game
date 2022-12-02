package server.socket;

import server.StartServer;
import server.entities.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

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
        sendMessage("SPACING" + Server.spacing);
        th.start();
    }
    public void run(){
        while (true){
            try {
                String str = listen();
                if (Objects.equals(str, null)){
                    int index = 0;
                    while (index < Server.clients.size()){
                        if (Objects.equals(Server.clients.get(index).player.id, player.id)){
                            StartServer.log(player.id + "zzz");
                            Server.clients.remove(index);
                        }
                        index = index + 1;
                    }
                    closeConnection();
                }else{
                    requests.add(str);
                }
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
        output.close();
        input.close();
    }
    public String listen() throws IOException {
        String str = null;
            str = input.readLine();
            StartServer.log(clientSocket.getRemoteSocketAddress() + ": " + str);
        return str;
    }
}
