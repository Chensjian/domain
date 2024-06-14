package com.chen.domain.service.impl;

import com.chen.domain.common.exception.ServiceException;
import com.chen.domain.dao.entity.AddressDO;
import com.chen.domain.dao.entity.CityDO;
import com.chen.domain.dao.entity.DomainCityInfoDO;
import com.chen.domain.dao.mapper.CityMapper;
import com.chen.domain.dto.req.CityAddAndUpdateReqDTO;
import com.chen.domain.dto.resp.CityListRespDTO;
import com.chen.domain.service.AddressService;
import com.chen.domain.service.CityService;
import com.chen.domain.service.DomainCityInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityMapper cityMapper;
    private final DomainCityInfoService domainCityInfoService;
    private final AddressService addressService;

    @Override
    public CityDO queryById(CityDO requestParam) {
        return cityMapper.queryById(requestParam);
    }

    @Override
    public List<CityListRespDTO> getList() {
        List<CityDO> cityDOList=cityMapper.getList();
        return cityDOList.stream().map(item->CityListRespDTO
                        .builder()
                        .id(item.getId())
                        .cityName(item.getCityName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void addCity(CityAddAndUpdateReqDTO requestParam) {
        if(!StringUtils.hasLength(requestParam.getCityName())){
            throw new ServiceException("执行失败，城市信息不能为空");
        }
        CityDO cityDO = new CityDO();
        cityDO.setCityName(requestParam.getCityName());
        cityDO.setDelFlag(0);
        cityDO.setCreateTime(new Date());
        cityMapper.insert(cityDO);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateCity(CityAddAndUpdateReqDTO requestParam) {
        if(Objects.isNull(requestParam.getId())||!StringUtils.hasLength(requestParam.getCityName())){
            throw new ServiceException("执行失败，城市信息不能为空");
        }
        Date nowTime = new Date();
        DomainCityInfoDO domainCityInfoUpdateDO = new DomainCityInfoDO();
        domainCityInfoUpdateDO.setUpdateTime(nowTime);
        domainCityInfoUpdateDO.setCityName(requestParam.getCityName());
        domainCityInfoUpdateDO.setCityId(requestParam.getId());
        domainCityInfoService.updateByCityId(domainCityInfoUpdateDO);
        AddressDO addressUpdateDO = new AddressDO();
        addressUpdateDO.setCityName(requestParam.getCityName());
        addressUpdateDO.setCityId(requestParam.getId());
        addressUpdateDO.setUpdateTime(nowTime);
        addressService.updateByCityId(addressUpdateDO);
        CityDO cityDO = new CityDO();
        cityDO.setId(requestParam.getId());
        cityDO.setCityName(requestParam.getCityName());
        cityDO.setDelFlag(0);
        cityDO.setCreateTime(new Date());
        cityMapper.update(cityDO);
    }

    @Override
    public void deleteById(Integer id) {
        DomainCityInfoDO domainCityInfoQueryDO = new DomainCityInfoDO();
        domainCityInfoQueryDO.setCityId(id);
        domainCityInfoQueryDO.setDelFlag(0);
        List<DomainCityInfoDO> domainCityInfoDOS = domainCityInfoService.queryList(domainCityInfoQueryDO);
        if(Objects.nonNull(domainCityInfoDOS)&&domainCityInfoDOS.size()>0){
            throw new ServiceException("执行失败，该城市信息已经被区域管理，无法删除");
        }
        cityMapper.deleteById(id);
    }
}
