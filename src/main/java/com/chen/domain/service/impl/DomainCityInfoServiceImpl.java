package com.chen.domain.service.impl;

import com.chen.domain.dao.entity.DomainCityInfoDO;
import com.chen.domain.dao.mapper.DomainCityInfoMapper;
import com.chen.domain.service.DomainCityInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DomainCityInfoServiceImpl implements DomainCityInfoService {

    private final DomainCityInfoMapper domainCityInfoMapper;
    @Override
    public List<DomainCityInfoDO> queryList(DomainCityInfoDO requestParam) {
        return domainCityInfoMapper.queryList(requestParam);
    }

    @Override
    public int delByIds(List<Integer> delDomainCityInfoIds) {
        return domainCityInfoMapper.delByIds(delDomainCityInfoIds);
    }

    @Override
    public int save(DomainCityInfoDO domainCityInfoDO) {
        return domainCityInfoMapper.insert(domainCityInfoDO);
    }

    @Override
    public DomainCityInfoDO queryByDomainIdAndCityId(DomainCityInfoDO requestParam) {
        return domainCityInfoMapper.queryByDomainIdAndCityId(requestParam);
    }

    @Override
    public int update(DomainCityInfoDO requestParam) {
        return domainCityInfoMapper.update(requestParam);
    }

    @Override
    public int deleteByDomainId(Integer domainId) {
        return domainCityInfoMapper.deleteByDomainId(domainId);
    }

    @Override
    public void updateByCityId(DomainCityInfoDO requestParam) {
        domainCityInfoMapper.updateByCityId(requestParam);
    }
}
