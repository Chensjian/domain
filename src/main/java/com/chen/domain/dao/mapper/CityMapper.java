package com.chen.domain.dao.mapper;

import com.chen.domain.dao.entity.CityDO;
import com.chen.domain.dao.entity.DomainCityInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityMapper {
    CityDO queryById(@Param("requestParam") CityDO requestParam);

    List<CityDO> getList();

    int insert(CityDO cityDO);

    int update(CityDO cityDO);

    int deleteById(Integer id);
}
