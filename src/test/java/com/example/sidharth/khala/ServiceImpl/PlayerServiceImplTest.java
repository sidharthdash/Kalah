package com.example.sidharth.khala.ServiceImpl;

import com.example.sidharth.khala.model.Pit;
import com.example.sidharth.khala.model.Player;
import com.example.sidharth.khala.util.Constants;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Sidharth on 7/6/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.example.sidharth.khala.ServiceImpl.PlayerServiceImpl.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerServiceImplTest {

    @Autowired
    PlayerServiceImpl playerService;

    @Test
    public  void Test1verifyCreatePlayer(){
        Player player=playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        assertNotNull(player);
        player=playerService.createPlayer("",Constants.pits,Constants.stonesineachpit);
        assertNull(player);
    }

    @Test
    public void Test2verifyCheckLastStoneInOwnEmptyPitRule(){
        Player player=playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        int numberOfStoneLeft=13;
        int pitNumber=1;
        assertEquals(pitNumber,playerService.checkLastStoneInOwnEmptyPitRule(player,numberOfStoneLeft,pitNumber));
        numberOfStoneLeft=1;
        assertNotEquals(pitNumber,
                playerService.checkLastStoneInOwnEmptyPitRule(player,numberOfStoneLeft,pitNumber));
        assertEquals(-1,playerService.checkLastStoneInOwnEmptyPitRule(player,numberOfStoneLeft,pitNumber));

    }

    @Test
    public void Test3verifyApplyLastStoneInOwnEmptyPitRule(){
        Player player1=playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        Player player2=playerService.createPlayer(Constants.PLAYER2,Constants.pits,Constants.stonesineachpit);

        HashMap<String, Player> playerMap = new HashMap<>();
        playerMap.put(Constants.PLAYER1,player1);
        playerMap.put(Constants.PLAYER2,player2);
        int numberOfStoneLeft=13;
        Pit pit = new Pit(numberOfStoneLeft);
        int pitNumber=1;
        player1.getPitArray()[pitNumber-1]=pit;

        assertEquals(0,player1.getKalha().getNumberOfStones());
        playerMap=playerService.applyLastStoneInOwnEmptyPitRule(playerMap,player1.getPlayerName(),pitNumber);

        assertEquals(6,player1.getKalha().getNumberOfStones());
        assertEquals(0,player2.getPitArray()[0].getNumberOfStones());
    }

    @Test
    public void  Test4verifyCreatePitArray(){
        Pit [] pitArray=playerService.createPitArray(10);
        assertEquals(10,pitArray.length);
        for (Pit pit:pitArray) {
            assertEquals(Constants.stonesineachpit,pit.getNumberOfStones());
        }
    }

    @Test
    public void Test5verifyIsNextMovePossible(){
        int numberOfStonesLeft=0;
        assertEquals(false,playerService.isNextMovePossible(numberOfStonesLeft));
        numberOfStonesLeft=10;
        assertEquals(true,playerService.isNextMovePossible(numberOfStonesLeft));

    }

    @Test
    public void Test6verifyIsGameFinished(){

        Player player1=playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        Player player2=playerService.createPlayer(Constants.PLAYER2,Constants.pits,Constants.stonesineachpit);

        HashMap<String, Player> playerMap = new HashMap<>();
        playerMap.put(Constants.PLAYER1,player1);
        playerMap.put(Constants.PLAYER2,player2);
        assertEquals(false,playerService.isGameFinished(playerMap));

        playerMap.get(Constants.PLAYER1).collectAllStonesAndPutInKhala();

        //player1.collectAllStonesAndPutInKhala();

        assertEquals(true,playerService.isGameFinished(playerMap));

    }

    @Test
    public void  Test7verifyDetermineWinner(){
        Player player1=playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        Player player2=playerService.createPlayer(Constants.PLAYER2,Constants.pits,Constants.stonesineachpit);
        HashMap<String, Player> playerMap = new HashMap<>();
        playerMap.put(Constants.PLAYER1,player1);
        playerMap.put(Constants.PLAYER2,player2);
        String drawPlayerName=player1.getPlayerName() + "   " + player2.getPlayerName();
        playerService.determineWinner(playerMap);

        assertEquals(drawPlayerName,playerService.determineWinner(playerMap));

        player1.insertIntoKhalaReturnStonesLeft(10);
        assertEquals(player1.getPlayerName(),playerService.determineWinner(playerMap));

    }

    @Test
    public  void Test8verifyGetOtherPLayerName(){
        Player player1=playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        Player player2=playerService.createPlayer(Constants.PLAYER2,Constants.pits,Constants.stonesineachpit);
        HashMap<String, Player> playerMap = new HashMap<>();
        playerMap.put(Constants.PLAYER1,player1);
        playerMap.put(Constants.PLAYER2,player2);
        assertEquals(Constants.PLAYER2,playerService.getOtherPLayerName(playerMap,player1.getPlayerName()));

    }


    @Test
    public  void  Test9verifySetNextPlayer(){

        String playerName=System.getProperty(Constants.CURRENT_PLAYER);
        playerService.setNextPlayer(Constants.PLAYER1);

        assertEquals(Constants.PLAYER1,System.getProperty(Constants.CURRENT_PLAYER));


    }


}