package com.example.sidharth.khala.model;


/**
 * Created by Sidharth on 6/17/17.
 */
public class Kalha {
    private int numberOfStones;

    public Kalha(int numberOfStones) {
        this.numberOfStones = numberOfStones;
    }

    public int getNumberOfStones() {
        return this.numberOfStones;
    }

    public void addStone(int stonesInKhala) {
        this.numberOfStones = this.numberOfStones + stonesInKhala;
    }
}
