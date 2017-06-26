package com.example.sidharth.khala.ServiceImpl;

import com.example.sidharth.khala.model.Kalha;
import com.example.sidharth.khala.model.Pit;
import com.example.sidharth.khala.model.Player;
import com.example.sidharth.khala.service.PlayerService;
import com.example.sidharth.khala.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.IntegerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Sidharth on 6/17/17.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Player createPlayer(String playerName, int numberOfPits, int stonesineachpit) {
        if (!StringUtils.isEmpty(playerName) && !StringUtils.isBlank(playerName)) {
            return new Player(playerName, new Kalha(0),
                    createPitArray(numberOfPits));
        }
        return null;
    }

    /*
        This method makes move for a player given in playerName
        It takes three steps into account
                    1. insert stones into own pits (if stones left)
                    2. insert stones into own khala (if stones left)
                    3. insert stones into other pits (if stones left)
                    4. continue step 1
     */
    @Override
    public HashMap makeMove(HashMap<String, Player> playerMap, String playerName, int pitNumber) {
        String otherPlayer = getOtherPLayerName(playerMap, playerName);
        Player player1 = playerMap.get(playerName);
        Player player2 = playerMap.get(otherPlayer);


        int numberOfStoneLeft = player1.pickAllStonesFromPit(pitNumber);
        int lastStoneInOwnEmptyPit = checkLastStoneInOwnEmptyPitRule(player1, numberOfStoneLeft, pitNumber);

        while (numberOfStoneLeft > 0) {


            numberOfStoneLeft = player1.insertIntoPitReturnStonesLeft(pitNumber, numberOfStoneLeft);

            if (!isNextMovePossible(numberOfStoneLeft)) {
                playerMap.put(player1.getPlayerName(), player1);
                setNextPlayer(player2.getPlayerName());
                if (lastStoneInOwnEmptyPit > 0) {
                    playerMap = applyLastStoneInOwnEmptyPitRule(playerMap, player1.getPlayerName(), lastStoneInOwnEmptyPit);
                }
                return playerMap;
            }


            numberOfStoneLeft = player1.insertIntoKhalaReturnStonesLeft(numberOfStoneLeft);
            if (!isNextMovePossible(numberOfStoneLeft)) {
                playerMap.put(player1.getPlayerName(), player1);
                setNextPlayer(player1.getPlayerName());
                return playerMap;
            }

            numberOfStoneLeft = player2.insertIntoPitReturnStonesLeft(0, numberOfStoneLeft);
            if (!isNextMovePossible(numberOfStoneLeft)) {
                playerMap.put(player2.getPlayerName(), player2);
                setNextPlayer(player2.getPlayerName());
                return playerMap;
            }
            pitNumber = 0;
        }
        return playerMap;

    }


    /*
        Returns the other playerName other than the playerName from the input PlayerMap
     */

    public String getOtherPLayerName(HashMap<String, Player> playerMap, String playerName) {
        final String[] nextPlayer = new String[1];
        playerMap.keySet().stream()
                .forEach(playerKey -> {
                    if (!playerKey.equalsIgnoreCase(playerName))
                        nextPlayer[0] = playerKey;
                });
        return nextPlayer[0];
    }

    /*
        This rule is to check if the game is finished i.e. a player
        pit contains no stones at all
     */
    @Override
    public boolean isGameFinished(HashMap<String, Player> playerMap) {
        final boolean result[] = new boolean[1];
        result[0] = false;
        playerMap.keySet().stream()
                .forEach(playerName -> {
                    if (!playerMap.get(playerName).containsStoneInPit()) {
                        result[0] = true;
                    }
                });
        return result[0];
    }

    /*
        This rule is to check if after the game is finished i.e. if any player has no
        stones in own Pit.
        The player contains seeds in his pits collect all and put in his khala.
        The winner is determine by the number of stone on a players khala
        The players name is returned who has the most stones
        In case of a draw ; name of both the players are returned
     */
    @Override
    public String determineWinner(HashMap<String, Player> playerMap) {

        Object playerName[] = playerMap.keySet().toArray();
        playerMap.keySet().stream()
                .forEach(player -> {
                    if (playerMap.get(player).containsStoneInPit()) {
                        playerMap.get(player).collectAllStonesAndPutInKhala();
                    }
                });
        if (playerMap.get(playerName[0]).getKalha().getNumberOfStones() ==
                playerMap.get(playerName[1]).getKalha().getNumberOfStones()) {
            return playerName[0] + "   " + playerName[1];
        } else if (playerMap.get(playerName[0]).getKalha().getNumberOfStones() >
                playerMap.get(playerName[1]).getKalha().getNumberOfStones()) {
            return playerName[0].toString();
        }
        return playerName[1].toString();
    }


    public boolean isNextMovePossible(int numberOfStonesLeft) {
        return numberOfStonesLeft > 0;
    }


    public Pit[] createPitArray(int size) {
        Pit[] pitArray = new Pit[size];
        for (int i = 0; i < size; i++) {
            pitArray[i] = new Pit(Constants.stonesineachpit);
        }
        return pitArray;
    }


    /* The player for the next move os chosen
       the player for the next move will not be changed if the
       the last stone is placed into own khala
       and will changed in all other cases
     */

    public void setNextPlayer(String nextPlayerName) {
        if (nextPlayerName.equalsIgnoreCase(Constants.PLAYER1) || nextPlayerName.equalsIgnoreCase(Constants.PLAYER2)) {
            System.setProperty(Constants.CURRENT_PLAYER.toString(), nextPlayerName);
            logger.debug("the current player is " + System.getProperty(Constants.CURRENT_PLAYER.toString()));
        }
    }

    /* This rule can be valid in two cases
       if the last stone is added on the pitNumber
       or in an empty pit before  pitNumber
       so first check if the numberOfStones are large enough to make a round to own pit
     */
    public int checkLastStoneInOwnEmptyPitRule(Player player1, int numberOfStoneLeft,
                                               int pitNumber) {
        int numberOfPits = Integer.valueOf(Constants.pits);
        int firstMagicNumber = numberOfPits * 2 + 1;
        int secondMagicNumber = numberOfStoneLeft - (player1.getNumberOfStonesToInsert(pitNumber, numberOfStoneLeft) +
                1 + numberOfPits);
        if (firstMagicNumber == numberOfStoneLeft) {
            return pitNumber;
        }
        boolean inRange = IntegerValidator.getInstance().isInRange(secondMagicNumber, 1, numberOfPits);
        if (inRange && (player1.getPitArray()[secondMagicNumber].getNumberOfStones() == 0)) {
            return secondMagicNumber;
        }
        return -1;
    }

    public HashMap<String, Player> applyLastStoneInOwnEmptyPitRule(HashMap<String, Player> playerMap,
                                                                   String player1, int pitNumber) {
        final int stonesCount[] = {0};
        playerMap.keySet().stream()
                .forEach(playerName -> {
                    if (!player1.equalsIgnoreCase(playerName)) {
                        stonesCount[0] = playerMap.get(playerName).getPitArray()[pitNumber - 1].pickAllStones();
                    }
                });
        playerMap.get(player1).getKalha().addStone(stonesCount[0]);
        return playerMap;
    }

}
