package com.chen.domain.controller;

import com.chen.domain.common.result.Result;
import com.chen.domain.common.result.Results;
import com.chen.domain.dto.req.CityAddAndUpdateReqDTO;
import com.chen.domain.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("city")
@CrossOrigin
public class CityController {

    private final CityService cityService;

    @GetMapping("list")
    public Result list(){
        return Results.success(cityService.getList());
    }

    @PostMapping
    public Result addCity(@RequestBody CityAddAndUpdateReqDTO requestParam){
        cityService.addCity(requestParam);
        return Results.success();
    }

    @PutMapping
    public Result updateCity(@RequestBody CityAddAndUpdateReqDTO requestParam){
        cityService.updateCity(requestParam);
        return Results.success();
    }
    @DeleteMapping("{id}")
    public Result delCity(@PathVariable Integer id){
        cityService.deleteById(id);
        return Results.success();
    }
}
