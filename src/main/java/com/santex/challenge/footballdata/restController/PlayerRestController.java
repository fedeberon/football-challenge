package com.santex.challenge.footballdata.restController;

import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Player;
import com.santex.challenge.footballdata.service.interfaces.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by federicoberon on 08/07/2019.
 */

@RestController
@RequestMapping("total-players")
@Api(value = "Player Management", description = "Operations pertaining to total players.")
public class PlayerRestController {

    private PlayerService service;

    @Autowired
    public PlayerRestController(PlayerService service) {
        this.service = service;
    }

    @GetMapping("{code}")
    @ApiOperation(value = "Wrapper<Player>", response = Wrapper.class)
    @ApiResponse(code = 200, message = "Total _*n*_")
    public ResponseEntity<Wrapper<Player>> totalPlayers(@PathVariable String code){
        Wrapper<Player> result = service.totalPlayers(code);

        return new ResponseEntity<>(result, result.getStatus());
    }

}
