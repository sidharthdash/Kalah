package com.example.sidharth.khala.model;

import com.example.sidharth.khala.ServiceImpl.PlayerServiceImpl;
import com.example.sidharth.khala.util.Constants;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Sidharth on 7/6/17.
 */

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class PlayerTest {
    @Autowired
    PlayerServiceImpl playerService;



    @Test
    public void Test1verifyPickAllStonesFromPit(){
        Player player = playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        int stoneReturned=player.pickAllStonesFromPit(1);
        assertEquals(stoneReturned,Constants.pits);
    }
    @Test
    public void Test2verifyInsertIntoPitReturnStonesLeft(){
        Player player = playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        int stoneReturned =6;
        stoneReturned=player.insertIntoPitReturnStonesLeft(1,stoneReturned);
        assertEquals(1,stoneReturned);
        assertEquals(player.getPitArray()[0].getNumberOfStones(),6);
        assertEquals(player.getKalha().getNumberOfStones(),0);
    }
    @Test
    public void Test3verifyInsertIntoKhalaReturnStonesLeft(){
        Player player = playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        int stoneReturned=1;
        stoneReturned=player.insertIntoKhalaReturnStonesLeft(stoneReturned);
        assertEquals(1,player.getKalha().getNumberOfStones());
        assertEquals(0,stoneReturned);

    }

    @Test
    public  void  Test4verifyContainsStoneInPit(){
        Player player = playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        assertEquals(true,player.containsStoneInPit());
    }

    @Test
    public void Test5verifyCollectAllStonesAndPutInKhala(){
        Player player = playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        player.collectAllStonesAndPutInKhala();
        assertEquals(36,player.getKalha().getNumberOfStones());
    }



}