package server.main;

import server.entities.Player;
import server.socket.Client;

public class PlayerRequest {
    public String type = "";
    public Client client;
    public PlayerRequest(String type, Client client){
        this.client = client;
        this.type = type;
    }
}
