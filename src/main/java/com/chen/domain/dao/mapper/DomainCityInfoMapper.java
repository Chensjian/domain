package com.chen.domain.dao.mapper;

import com.chen.domain.dao.entity.DomainCityInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DomainCityInfoMapper {
    List<DomainCityInfoDO> queryList(@Param("requestParam") DomainCityInfoDO requestParam);

    int delByIds(@Param("ids") List<Integer> delDomainCityInfoIds);

    int insert(@Param("requestParam") DomainCityInfoDO domainCityInfoDO);

    DomainCityInfoDO queryByDomainIdAndCityId(@Param("requestParam") DomainCityInfoDO requestParam);

    int update(@Param("requestParam") DomainCityInfoDO requestParam);

    int deleteByDomainId(@Param("domainId") Integer domainId);

    int updateByCityId(@Param("requestParam") DomainCityInfoDO requestParam);
}
