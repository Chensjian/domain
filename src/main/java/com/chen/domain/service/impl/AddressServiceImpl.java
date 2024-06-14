package com.chen.domain.service.impl;

import com.chen.domain.dao.entity.AddressDO;
import com.chen.domain.dao.mapper.AddressMapper;
import com.chen.domain.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;
    @Override
    public List<AddressDO> queryList(AddressDO requestParam) {
        return addressMapper.queryList(requestParam);
    }

    @Override
    public int delByDomainCityInfoIds(List<Integer> delDomainCityInfoIds) {
        return addressMapper.delByDomainCityInfoIds(delDomainCityInfoIds);
    }

    @Override
    public int delByIds(List<Integer> delAddressIdList) {
        return addressMapper.delByIds(delAddressIdList);
    }

    @Override
    public int batchSave(List<AddressDO> addressAddDOList) {
        return addressMapper.batchSave(addressAddDOList);
    }

    @Override
    public int update(AddressDO addressUpdateDO) {
        return addressMapper.update(addressUpdateDO);
    }

    @Override
    public int updateByIds(AddressDO addressUpdateDO, List<Integer> addressIds) {
        return addressMapper.updateByIds(addressUpdateDO,addressIds);
    }

    @Override
    public int deleteByDomainId(Integer domainId) {
        return addressMapper.deleteByDomainId(domainId);
    }

    @Override
    public int updateByCityId(AddressDO addressUpdateDO) {
        return addressMapper.updateByCityId(addressUpdateDO);
    }


}
