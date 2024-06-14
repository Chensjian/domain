package com.chen.domain.dao.mapper;

import com.chen.domain.dao.entity.AddressDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<AddressDO> queryList(@Param("requestParam") AddressDO requestParam);

    int delByDomainCityInfoIds(@Param("delDomainCityInfoIds") List<Integer> delDomainCityInfoIds);

    int delByIds(@Param("ids") List<Integer> delAddressIdList);


    int batchSave(@Param("addressDOList") List<AddressDO> addressAddDOList);

    int update(@Param("requestParam") AddressDO requestParam);


    int updateByIds(@Param("requestParam") AddressDO addressUpdateDO,@Param("ids") List<Integer> addressIds);

    int deleteByDomainId(@Param("domainId") Integer domainId);

    int updateByCityId(@Param("requestParam") AddressDO requestParam);
}
