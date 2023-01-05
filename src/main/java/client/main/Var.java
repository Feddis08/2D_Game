package client.main;

import client.blocks.Block;
import client.entities.Player;

import java.util.ArrayList;
import java.util.Objects;

public class Var {
    public static ArrayList<Block> blocksToRender = new ArrayList<>();

    public static Boolean waiting_for_view_port = false;
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<PlayerRequest> playerRequests = new ArrayList<>();

    public static Player get_player(Integer id) {
        Integer index = 0;
        Player p = null;
        while (index < players.size()) {
            Player p2 = players.get(index);
            if (Objects.equals(p2.id, id)){
                p = p2;
            }
            index = index + 1;
        }
        return p;
    }
    public static void remove_player(Integer id) {
        int index = 0;
        while (index < players.size()) {
            Player p2 = players.get(index);
            if (Objects.equals(p2.id, id)){
                players.remove(index);
            }
            index = index + 1;
        }
    }
}
