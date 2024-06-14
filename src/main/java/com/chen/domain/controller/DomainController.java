package com.chen.domain.controller;

import com.chen.domain.common.result.Result;
import com.chen.domain.common.result.Results;
import com.chen.domain.dto.req.DomainAddAndUpdateReqDTO;
import com.chen.domain.dto.req.DomainPageReqDTO;
import com.chen.domain.dto.req.DomainUpdateReqDTO;
import com.chen.domain.service.DomainService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("domain")
@AllArgsConstructor
@CrossOrigin
public class DomainController {

    private final DomainService domainService;

    @GetMapping("page")
    public Result page( DomainPageReqDTO requestParam){
        return Results.success(domainService.pageList(requestParam));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id){
        return Results.success(domainService.getDomainById(id));
    }

    @PutMapping
    public Result updateDomain(@RequestBody DomainAddAndUpdateReqDTO requestParam){
        domainService.updateDomain(requestParam);
        return Results.success().setMessage("执行成功!");
    }

    @PostMapping
    public Result addDomain(@RequestBody DomainAddAndUpdateReqDTO requestParam){
        domainService.addDomain(requestParam);
        return Results.success().setMessage("执行成功!");
    }

    @DeleteMapping("{id}")
    public Result deleteDomain(@PathVariable Integer id){
        domainService.deleteDomain(id);
        return Results.success().setMessage("执行成功!");
    }


}
