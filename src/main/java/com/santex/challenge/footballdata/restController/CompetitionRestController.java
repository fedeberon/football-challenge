package com.santex.challenge.footballdata.restController;

import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Competition;
import com.santex.challenge.footballdata.service.interfaces.CompetitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by federicoberon on 01/07/2019.
 */
@RestController
@RequestMapping("import-league")
@Api(value = "Competition Management", description = "Operations pertaining to import league.")
public class CompetitionRestController {

    @Autowired
    private CompetitionService service;

    @GetMapping("{code}")
    @ApiOperation(value = "Wrapper<Competition>", response = Wrapper.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully imported"),
            @ApiResponse(code = 409, message = "League already imported"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 504, message = "Server Error")
    })
    public ResponseEntity<Wrapper<Competition>> importCode(@PathVariable String code){
        Wrapper<Competition> wrapper = service.importCode(code);

        return new ResponseEntity<>(wrapper, wrapper.getStatus());
    }

}
