package com.example.sidharth.khala.model;

/**
 * Created by Sidharth on 6/17/17.
 * <p>
 * the Model of a Pit
 * if need to add stone increase numberOfStones by one
 * pickAllStones remove all the stones from the pit
 */

public class Pit {
    int numberOfStones;


    public Pit(int numberOfStones) {
        this.numberOfStones = numberOfStones;

    }

    public void addStone() {
        this.numberOfStones++;
    }

    public int pickAllStones() {
        int noOfStone = this.numberOfStones;
        this.numberOfStones = 0;
        return noOfStone;
    }

    public int getNumberOfStones() {
        return numberOfStones;
    }

}
