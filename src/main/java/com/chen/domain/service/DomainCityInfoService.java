package com.chen.domain.service;

import com.chen.domain.dao.entity.DomainCityInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DomainCityInfoService {
    List<DomainCityInfoDO> queryList(DomainCityInfoDO cityQueryDO);

    int delByIds(List<Integer> delDomainCityInfoIds);

    int save(DomainCityInfoDO domainCityInfoDO);

    DomainCityInfoDO queryByDomainIdAndCityId(DomainCityInfoDO requestParam);

    int update(DomainCityInfoDO requestParam);

    int deleteByDomainId(Integer domainId);

    void updateByCityId(DomainCityInfoDO domainCityInfoUpdateDO);
}
