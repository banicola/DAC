package sr.dac.main;

import java.util.UUID;

public class PlayersDAC {
    UUID player;

    int stats_deaths;
    int stats_wins;
    int stats_dac;
    int stats_game_played;

    public PlayersDAC(UUID player){
        this.player = player;
    }
}
