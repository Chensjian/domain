package com.chen.domain.service;

import com.chen.domain.dao.entity.CityDO;
import com.chen.domain.dao.entity.DomainCityInfoDO;
import com.chen.domain.dto.req.CityAddAndUpdateReqDTO;
import com.chen.domain.dto.resp.CityListRespDTO;

import java.util.List;

public interface CityService {

    CityDO queryById(CityDO cityQueryDO);

    List<CityListRespDTO> getList();

    void addCity(CityAddAndUpdateReqDTO requestParam);

    void updateCity(CityAddAndUpdateReqDTO requestParam);

    void deleteById(Integer id);
}
