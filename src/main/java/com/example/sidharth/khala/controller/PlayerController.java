package com.example.sidharth.khala.controller;

import com.example.sidharth.khala.ServiceImpl.PlayerServiceImpl;
import com.example.sidharth.khala.model.Player;
import com.example.sidharth.khala.repo.ReturnCode;
import com.example.sidharth.khala.util.Constants;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by Sidharth on 6/19/17.
 */
@Controller
@Slf4j
@RequestMapping("play")
@Api(value = "Kalah" , description = "Welcome to the game of Kalah")
public class PlayerController {

    @Autowired
    private PlayerServiceImpl playerService;
    private HashMap<String, Player> playerMap;



    @ApiOperation(value = "Starts the Game",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/start", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object createPlayers() {
        playerMap = new HashMap<>();
        Player player1 = playerService.createPlayer(Constants.PLAYER1,Constants.pits,Constants.stonesineachpit);
        Player player2 = playerService.createPlayer(Constants.PLAYER2,Constants.pits,Constants.stonesineachpit);
        playerMap.put(player1.getPlayerName(), player1);
        playerMap.put(player2.getPlayerName(), player2);
        System.setProperty(Constants.CURRENT_PLAYER, player1.getPlayerName());
        return playerMap;
    }

    /*
    The sample request is


     */

    @ApiOperation(value = "Make The Move",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @ResponseBody
    public Object makeMoves(@RequestBody String moveRequest) {
        try {
            JSONObject obj = new JSONObject(moveRequest);
            int pitNumber = Integer.valueOf((String) obj.get(Constants.PITNUMBER.toLowerCase()));
            if (pitNumber < 0 || pitNumber > Constants.pits) {
                throw new NumberFormatException();
            }

            playerService.makeMove(playerMap, System.getProperty(Constants.
                    CURRENT_PLAYER), pitNumber);

            if (playerService.isGameFinished(playerMap)){
               return playerService.determineWinner(playerMap);
            }



        } catch (NumberFormatException e) {
            return ReturnCode.PIT_NUMBER_MUST_BE_FROM_1_TO_6;
        } catch (JSONException e) {
            return ReturnCode.UNPARSABLE_JSON;
        }
        if (playerService.isGameFinished(playerMap)) {

        }

        return playerMap;
    }

}
