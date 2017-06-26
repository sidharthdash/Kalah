package com.example.sidharth.configclient;

import com.example.sidharth.khala.model.Pit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sidharth on 6/23/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("classpath:game.properties")
public class PitTest {
    @Test
    public void verifyPit(){
        Pit pit = new Pit(6);
        assertEquals(6,pit.getNumberOfStones());
        assertEquals(6,pit.pickAllStones());
        assertEquals(0,pit.getNumberOfStones());
        pit.addStone();
        assertEquals(1,pit.getNumberOfStones());
    }

}
