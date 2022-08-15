package server.main;

import client.entities.Player;

public class PlayerRequest {
    public String type = "";
    public Player player;
    public PlayerRequest(String type, Player player){
        this.player = player;
        this.type = type;
    }
}
