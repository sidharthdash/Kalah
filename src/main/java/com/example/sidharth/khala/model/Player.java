package com.example.sidharth.khala.model;

import com.example.sidharth.khala.util.Constants;

/**
 * Created by Sidharth on 6/17/17.
 */
public class Player {
    private String playerName;
    private Kalha kalha;
    private Pit[] pitArray;


    public Player(String playerName, Kalha kalha, Pit[] pitArray) {
        this.playerName = playerName;
        this.kalha = kalha;
        this.pitArray = pitArray;
    }

    public Kalha getKalha() {
        return kalha;
    }

    public Pit[] getPitArray() {
        return pitArray;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int pickAllStonesFromPit(int pitNumber) {
        return this.pitArray[pitNumber - 1].pickAllStones();
    }

    /*
    Insert stones from pitNumber till last pit  of the player
    Return the number of stones after the stones are inserted
    */
    public int insertIntoPitReturnStonesLeft(int pitNumber, int numberOfStones) {
        int stonesToInsert = getNumberOfStonesToInsert(pitNumber, numberOfStones);
        for (int i = pitNumber; i <= stonesToInsert + pitNumber - 1; i++) {
            pitArray[i].addStone();
        }
        return numberOfStones - stonesToInsert;
    }

    /*
    Insert stones into players Khala
    Return the number of stones after the stones are inserted
    */
    public int insertIntoKhalaReturnStonesLeft(int numberOfStones) {
        if (numberOfStones >= 1) {
            this.getKalha().addStone(1);
            return numberOfStones - 1;
        }
        return numberOfStones;
    }

    /*
        Return true if stones exist in a Players pit
     */
    public boolean containsStoneInPit() {
        for (Pit pit : this.pitArray) {
            if (pit.numberOfStones > 0)
                return true;
        }
        return false;
    }

    /*
        Collect  stones from all the PIT's of the player
         and insert into own khala
     */
    public void collectAllStonesAndPutInKhala() {
        int stoneCount = 0;
        for (Pit pit : this.pitArray) {
            stoneCount += pit.pickAllStones();
        }
        this.kalha.addStone(stoneCount);
    }

    public int getNumberOfStonesToInsert(int pitNumber, int numberOfStones) {
        int numberOfPits = Constants.pits;
        return numberOfStones > numberOfPits - pitNumber ?
                numberOfPits - pitNumber : numberOfStones;
    }
}
