package client.main;

import client.StartClient;

import java.io.IOException;

public class Tools {

    public static Boolean checkCollision(Integer x1, Integer y1, Integer x2, Integer y2, Integer x3, Integer y3, Integer x4, Integer y4){
        Boolean result = false;
        if (x2 >= x3 && x1 <= x4) {
            if (y1 <= y4 && y3 <= y2) {
                result = true;
            }
        }
        return result;
    }
    public static void player_go(String direction) throws IOException {
        Var.get_player(StartClient.id).change_move_direction(direction);
        StartClient.client.sendMessage("go" + StartClient.client.spacing + direction);
        //StartClient.client.sendMessage("getViewport");

    }
    public static void player_dig() throws IOException {

        StartClient.client.sendMessage("dig");
        //StartClient.client.sendMessage("getViewport");

    }

    public static int get_tick_sleep(long current_time, long server_time, int tick_rate){
        int diff = (int)  (current_time - server_time);
        int ticks_passed = diff / tick_rate;
        int next_tick_time = (int) server_time + (tick_rate * (ticks_passed + 1));
        int sleep_time = next_tick_time - (int) current_time;
        return sleep_time;
    }

}
