package com.chen.domain.service;

import com.chen.domain.dao.entity.AddressDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressService {
    List<AddressDO> queryList(AddressDO requestParam);

    int delByDomainCityInfoIds(List<Integer> delDomainCityInfoIds);

    int delByIds(List<Integer> delAddressIdList);

    int batchSave(List<AddressDO> addressAddDOList);

    int update(AddressDO addressUpdateDO);
    int updateByIds(AddressDO addressUpdateDO, List<Integer> addressIds);

    int deleteByDomainId(Integer id);

    int updateByCityId(AddressDO addressUpdateDO);
}
