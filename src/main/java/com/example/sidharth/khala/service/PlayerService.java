package com.example.sidharth.khala.service;

import com.example.sidharth.khala.model.Player;

import java.util.HashMap;

/**
 * Created by Sidharth on 6/17/17.
 */
public interface PlayerService {

    Player createPlayer(String playerName, int numberOfPits, int stonesineachpit);

    HashMap makeMove(HashMap<String, Player> playerMap, String playerName, int pitnumber);

    boolean isGameFinished(HashMap<String, Player> playerMap);

    String determineWinner(HashMap<String, Player> playerMap);


}
