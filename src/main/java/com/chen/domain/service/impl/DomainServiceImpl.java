package com.chen.domain.service.impl;

import com.chen.domain.common.exception.ServiceException;
import com.chen.domain.dao.entity.AddressDO;
import com.chen.domain.dao.entity.CityDO;
import com.chen.domain.dao.entity.DomainCityInfoDO;
import com.chen.domain.dao.entity.DomainDO;
import com.chen.domain.dao.mapper.DomainMapper;
import com.chen.domain.dto.req.*;
import com.chen.domain.dto.resp.AddressQueryRespDTO;
import com.chen.domain.dto.resp.DomainCityInfoQueryRespDTO;
import com.chen.domain.dto.resp.DomainPageRespDTO;
import com.chen.domain.dto.resp.DomainQueryRespDTO;
import com.chen.domain.service.AddressService;
import com.chen.domain.service.CityService;
import com.chen.domain.service.DomainCityInfoService;
import com.chen.domain.service.DomainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DomainServiceImpl implements DomainService {

    private final DomainMapper domainMapper;
    private final DomainCityInfoService domainCityInfoService;
    private final AddressService addressService;
    private final CityService cityService;
    @Override
    public PageInfo<DomainPageRespDTO> pageList(DomainPageReqDTO requestParam) {
        if (Objects.isNull(requestParam)){
            throw new ServiceException("请求参数不能为空!");
        }
        PageHelper.startPage(requestParam.getPageNum(),requestParam.getPageSize());
        requestParam.setDelFlag(0);
        List<DomainPageRespDTO> domainPageRespDTOList=new ArrayList<>();
        List<DomainDO> domainDOList =domainMapper.queryList(requestParam);
        PageInfo<DomainDO> dtoPageInfo = new PageInfo<>(domainDOList);
        PageInfo<DomainPageRespDTO> pageInfo = new PageInfo<>();
        if(Objects.isNull(domainDOList)||domainDOList.size()==0){
            return pageInfo;
        }
        for(DomainDO domainDO:domainDOList){
            List<String> cityNameList=new ArrayList<>();
            List<String> addressList=new ArrayList<>();
            DomainCityInfoDO cityQueryDO = new DomainCityInfoDO();
            cityQueryDO.setDomainId(domainDO.getId());
            cityQueryDO.setCityName(requestParam.getCityName());
            cityQueryDO.setDelFlag(0);
            List<DomainCityInfoDO> domainCityInfoDOList = domainCityInfoService.queryList(cityQueryDO);
            if(StringUtils.hasLength(requestParam.getCityName())&&
                    (Objects.isNull(domainCityInfoDOList)||domainCityInfoDOList.size()==0)){
                return pageInfo;
            }
            for(DomainCityInfoDO domainCityInfoDO : domainCityInfoDOList){
                cityNameList.add(domainCityInfoDO.getCityName());
                AddressDO addressQueryDO = new AddressDO();
                addressQueryDO.setDomainCityInfoId(domainCityInfoDO.getId());
                addressQueryDO.setAddress(requestParam.getAddress());
                addressQueryDO.setDelFlag(0);
                List<AddressDO> addressDOList=addressService.queryList(addressQueryDO);
                List<String> addList = addressDOList.stream().map(AddressDO::getAddress).collect(Collectors.toList());
                if(Objects.nonNull(addList)&&addList.size()>0){
                    addressList.addAll(addList);
                }
            }
            if(StringUtils.hasLength(requestParam.getAddress())&&
                    (Objects.isNull(addressList)||addressList.size()==0)){
                return pageInfo;
            }
            DomainPageRespDTO domainPageRespDTO = DomainPageRespDTO
                    .builder()
                    .domainId(domainDO.getId())
                    .domainName(domainDO.getDomainName())
                    .createTime(domainDO.getCreateTime())
                    .cityNameList(cityNameList)
                    .addressList(addressList)
                    .build();
            domainPageRespDTOList.add(domainPageRespDTO);
        }
        BeanUtils.copyProperties(dtoPageInfo,pageInfo);
        pageInfo.setList(domainPageRespDTOList);
        return pageInfo;
    }

    @Override
    public DomainQueryRespDTO getDomainById(Integer id) {
        DomainDO domainQueryDO = new DomainDO();
        domainQueryDO.setId(id);
        domainQueryDO.setDelFlag(0);
        DomainDO domainDO=domainMapper.queryById(domainQueryDO);
        if(Objects.isNull(domainDO)){
            throw new ServiceException("该区域不存在");
        }
        DomainCityInfoDO cityQueryDO=new DomainCityInfoDO();
        cityQueryDO.setDomainId(domainDO.getId());
        cityQueryDO.setDelFlag(0);
        List<DomainCityInfoQueryRespDTO> domainCityInfoQueryRespDTOList =new ArrayList<>();
        List<DomainCityInfoDO> domainCityInfoDOList = domainCityInfoService.queryList(cityQueryDO);
        for(DomainCityInfoDO domainCityInfoDO : domainCityInfoDOList){
            AddressDO addressQueryDO = new AddressDO();
            addressQueryDO.setDomainCityInfoId(domainCityInfoDO.getId());
            addressQueryDO.setDelFlag(0);
            List<AddressDO> addressDOList = addressService.queryList(addressQueryDO);
            List<AddressQueryRespDTO> addressQueryRespDTOList = addressDOList
                    .stream()
                    .map(addressDO -> AddressQueryRespDTO
                            .builder()
                            .addressId(addressDO.getId())
                            .addressName(addressDO.getAddress())
                            .domainCityInfoId(addressDO.getDomainCityInfoId())
                            .cityName(addressDO.getCityName())
                            .build())
                            .collect(Collectors.toList());
            DomainCityInfoQueryRespDTO domainCityInfoQueryRespDTO = DomainCityInfoQueryRespDTO
                    .builder()
                    .domainCityInfoId(domainCityInfoDO.getId())
                    .cityId(domainCityInfoDO.getCityId())
                    .cityName(domainCityInfoDO.getCityName())
                    .domainId(domainCityInfoDO.getDomainId())
                    .domainName(domainCityInfoDO.getDomainName())
                    .addressList(addressQueryRespDTOList)
                    .build();
            domainCityInfoQueryRespDTOList.add(domainCityInfoQueryRespDTO);
        }
        return DomainQueryRespDTO
                .builder()
                .domainId(domainDO.getId())
                .domainName(domainDO.getDomainName())
                .createTime(domainDO.getCreateTime())
                .cityList(domainCityInfoQueryRespDTOList)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateDomain(DomainAddAndUpdateReqDTO requestParam) {
        if(Objects.isNull(requestParam.getDomainId())){
            throw new ServiceException("区域信息不能为空!");
        }
        DomainDO domainQueryDO = new DomainDO();
        domainQueryDO.setId(requestParam.getDomainId());
        domainQueryDO.setDelFlag(0);
        DomainDO domainDO = domainMapper.queryById(domainQueryDO);
        if (Objects.isNull(domainDO)){
            throw new ServiceException("区域不存在!");
        }
        Date nowTime = new Date();
        String domainName = domainDO.getDomainName();
        if(!domainName.equals(requestParam.getDomainName())){
            domainName= requestParam.getDomainName();
            DomainDO domainUpdateDO=new DomainDO();
            domainUpdateDO.setId(requestParam.getDomainId());
            domainUpdateDO.setDomainName(domainName);
            domainUpdateDO.setUpdateTime(nowTime);
            domainMapper.updateById(domainUpdateDO);
        }
        List<DomainCityInfoAddAndUpdateReqDTO> domainCityInfoAddAndUpdateReqDTOList = requestParam.getCityList();
        if(Objects.isNull(domainCityInfoAddAndUpdateReqDTOList)&&domainCityInfoAddAndUpdateReqDTOList.size()==0){
            return;
        }
        Map<Integer,DomainCityInfoAddAndUpdateReqDTO> domainCityInfoAddAndUpdateReqDTOMap=new HashMap<>();
        List<DomainCityInfoAddAndUpdateReqDTO> updateDomainCityDTOList=new ArrayList<>();
        for(DomainCityInfoAddAndUpdateReqDTO item:domainCityInfoAddAndUpdateReqDTOList){
            if(!domainCityInfoAddAndUpdateReqDTOMap.containsKey(item.getCityId())){
                domainCityInfoAddAndUpdateReqDTOMap.put(item.getCityId(),item);
            }else{
                DomainCityInfoAddAndUpdateReqDTO domainCItyDTO = domainCityInfoAddAndUpdateReqDTOMap.get(item.getCityId());
                Integer domainCityInfoId=null;
                if(Objects.nonNull(domainCItyDTO.getDomainCityInfoId())){
                    domainCityInfoId=domainCItyDTO.getDomainCityInfoId();
                }
                if(Objects.nonNull(item.getDomainCityInfoId())&&Objects.nonNull(domainCityInfoId)){
                    CityDO cityQueryDO=new CityDO();
                    cityQueryDO.setId(item.getCityId());
                    cityQueryDO.setDelFlag(0);
                    CityDO cityDO = cityService.queryById(cityQueryDO);
                    if(Objects.isNull(cityDO)){
                        throw new ServiceException("执行失败，城市信息不存在!");
                    }
                    AddressDO addressQueryDO = new AddressDO();
                    addressQueryDO.setDomainCityInfoId(item.getDomainCityInfoId());
                    addressQueryDO.setDelFlag(0);
                    List<AddressDO> addressDOList = addressService.queryList(addressQueryDO);
                    if(Objects.nonNull(addressDOList)&&addressDOList.size()>0){
                        AddressDO addressUpdateDO=new AddressDO();
                        addressUpdateDO.setDomainCityInfoId(domainCityInfoId);
                        addressUpdateDO.setCityName(cityDO.getCityName());
                        addressUpdateDO.setUpdateTime(nowTime);
                        List<Integer> addressIds = addressDOList.stream().map(addressDO -> addressDO.getId()).collect(Collectors.toList());
                        addressService.updateByIds(addressUpdateDO,addressIds);
                    }
                    domainCityInfoService.delByIds(Arrays.asList(item.getDomainCityInfoId()));
                }
                List<AddressAddAndUpdateReqDTO> addressList1 = domainCItyDTO.getAddressList();
                List<AddressAddAndUpdateReqDTO> addressList2 = item.getAddressList();
                if(Objects.isNull(addressList1)&&Objects.isNull(addressList2)){
                    continue;
                }
                if(Objects.isNull(addressList1)){
                    domainCItyDTO.setAddressList(addressList1);
                    continue;
                }
                if(Objects.isNull(addressList2)){
                    domainCItyDTO.setAddressList(addressList2);
                    continue;
                }
                addressList1.addAll(addressList2);
                domainCItyDTO.setAddressList(addressList1);
            }
        }
        Set<Integer> domainCityMapKeySet = domainCityInfoAddAndUpdateReqDTOMap.keySet();
        for(Integer key:domainCityMapKeySet){
            updateDomainCityDTOList.add(domainCityInfoAddAndUpdateReqDTOMap.get(key));
        }
        DomainCityInfoDO domainCityInfoQueryDO = new DomainCityInfoDO();
        domainCityInfoQueryDO.setDomainId(requestParam.getDomainId());
        domainCityInfoQueryDO.setDelFlag(0);
        List<DomainCityInfoDO> domainCityInfoDOList = domainCityInfoService.queryList(domainCityInfoQueryDO);
        Map<Integer,DomainCityInfoAddAndUpdateReqDTO> domainCityInfoIdMap=new HashMap<>();
        List<AddressDO> addressAddDOList=new ArrayList<>();

        for(DomainCityInfoAddAndUpdateReqDTO item:updateDomainCityDTOList){
            if(Objects.isNull(item.getDomainCityInfoId())){
                if(Objects.isNull(item.getCityId())){
                    throw new ServiceException("执行失败，请选择区域城市!");
                }
                CityDO cityQueryDO=new CityDO();
                cityQueryDO.setId(item.getCityId());
                cityQueryDO.setDelFlag(0);
                CityDO cityDO = cityService.queryById(cityQueryDO);
                if(Objects.isNull(cityDO)){
                    throw new ServiceException("执行失败，城市信息不存在!");
                }
                DomainCityInfoDO domainCityInfoQueryDOByCityId=new DomainCityInfoDO();
                domainCityInfoQueryDOByCityId.setDomainId(domainDO.getId());
                domainCityInfoQueryDOByCityId.setCityId(cityDO.getId());
                domainCityInfoQueryDOByCityId.setDelFlag(0);
                Integer domainCityInfoId=null;
                DomainCityInfoDO domainCityInfoDO =domainCityInfoService.queryByDomainIdAndCityId(domainCityInfoQueryDOByCityId);
                if(Objects.isNull(domainCityInfoDO)){
                    DomainCityInfoDO domainCityInfoAddDO = new DomainCityInfoDO();
                    domainCityInfoAddDO.setDomainId(domainDO.getId());
                    domainCityInfoAddDO.setDomainName(domainDO.getDomainName());
                    domainCityInfoAddDO.setCityId(item.getCityId());
                    domainCityInfoAddDO.setCityName(cityDO.getCityName());
                    domainCityInfoAddDO.setDelFlag(0);
                    domainCityInfoAddDO.setCreateTime(nowTime);
                    domainCityInfoService.save(domainCityInfoAddDO);
                    domainCityInfoId=domainCityInfoAddDO.getId();
                }else{
                    domainCityInfoId=domainCityInfoDO.getId();
                }
                List<AddressAddAndUpdateReqDTO> addressAddList = item.getAddressList();
                if(Objects.nonNull(addressAddList)&&addressAddList.size()>0){
                    for(AddressAddAndUpdateReqDTO addressAddAndUpdateReqDTO:addressAddList){
                        if(!StringUtils.hasLength(addressAddAndUpdateReqDTO.getAddressName())){
                            continue;
                        }
                        AddressDO addressAddDO = new AddressDO();
                        addressAddDO.setAddress(addressAddAndUpdateReqDTO.getAddressName());
                        addressAddDO.setDomainCityInfoId(domainCityInfoId);
                        addressAddDO.setCityId(cityDO.getId());
                        addressAddDO.setCityName(cityDO.getCityName());
                        addressAddDO.setCreateTime(nowTime);
                        addressAddDO.setDelFlag(0);
                        addressAddDOList.add(addressAddDO);
                    }
                }
                continue;
            }
            domainCityInfoIdMap.put(item.getDomainCityInfoId(),item);
        }
        List<Integer> delDomainCityInfoIdList=new ArrayList<>();
        for(DomainCityInfoDO domainCityInfoDO:domainCityInfoDOList){
            if(!domainCityInfoIdMap.containsKey(domainCityInfoDO.getId())){
                delDomainCityInfoIdList.add(domainCityInfoDO.getId());
                continue;
            }
            DomainCityInfoAddAndUpdateReqDTO domainCityInfoAddAndUpdateReqDTO = domainCityInfoIdMap.get(domainCityInfoDO.getId());
            Integer domainCityInfoId=domainCityInfoAddAndUpdateReqDTO.getDomainCityInfoId();
            CityDO cityQueryDO=new CityDO();
            cityQueryDO.setId(domainCityInfoAddAndUpdateReqDTO.getCityId());
            cityQueryDO.setDelFlag(0);
            CityDO cityDO = cityService.queryById(cityQueryDO);
            if(Objects.isNull(cityDO)){
                throw new ServiceException("执行失败，城市信息不存在!");
            }
            if(!domainCityInfoAddAndUpdateReqDTO.getCityId().equals(domainCityInfoDO.getCityId())){
                DomainCityInfoDO domainCityInfoQueryDOByCityId=new DomainCityInfoDO();
                domainCityInfoQueryDOByCityId.setCityId(cityDO.getId());
                domainCityInfoQueryDOByCityId.setDomainId(domainDO.getId());
                domainCityInfoQueryDOByCityId.setDelFlag(0);
                DomainCityInfoDO domainCityInfoDOByCityId =domainCityInfoService.queryByDomainIdAndCityId(domainCityInfoQueryDOByCityId);
                if(Objects.isNull(domainCityInfoDOByCityId)){
                    DomainCityInfoDO domainCityInfoUpdateDO = new DomainCityInfoDO();
                    domainCityInfoUpdateDO.setId(domainCityInfoAddAndUpdateReqDTO.getDomainCityInfoId());
                    domainCityInfoUpdateDO.setCityId(cityDO.getId());
                    domainCityInfoUpdateDO.setCityName(cityDO.getCityName());
                    domainCityInfoUpdateDO.setUpdateTime(nowTime);
                    domainCityInfoService.update(domainCityInfoUpdateDO);
                    AddressDO addressQueryDO=new AddressDO();
                    addressQueryDO.setDomainCityInfoId(domainCityInfoAddAndUpdateReqDTO.getDomainCityInfoId());
                    addressQueryDO.setDelFlag(0);
                    List<AddressDO> addressDOList = addressService.queryList(addressQueryDO);
                    List<Integer> addressIds = addressDOList.stream().map(each -> each.getId()).collect(Collectors.toList());
                    AddressDO addressUpdateDO = new AddressDO();
                    addressUpdateDO.setCityName(cityDO.getCityName());
                    addressUpdateDO.setUpdateTime(nowTime);
                    addressService.updateByIds(addressUpdateDO,addressIds);
                }else{
                    domainCityInfoId=domainCityInfoDOByCityId.getId();
                }
            }

            List<AddressAddAndUpdateReqDTO> addressList = domainCityInfoAddAndUpdateReqDTO.getAddressList();
            if(Objects.nonNull(addressList)&& addressList.size()>0) {
                AddressDO addressQueryDO=new AddressDO();
                addressQueryDO.setDomainCityInfoId(domainCityInfoId);
                addressQueryDO.setDelFlag(0);
                List<AddressDO> addressDOList = addressService.queryList(addressQueryDO);
                Map<Integer,AddressDO> addressDOMap=new HashMap<>();
                for(AddressAddAndUpdateReqDTO addressAddAndUpdateReqDTO:addressList) {
                    if(Objects.isNull(addressAddAndUpdateReqDTO.getAddressId())) {
                        if(!StringUtils.hasLength(addressAddAndUpdateReqDTO.getAddressName())){
                            continue;
                        }
                        AddressDO addressAddDO = new AddressDO();
                        addressAddDO.setAddress(addressAddAndUpdateReqDTO.getAddressName());
                        addressAddDO.setDomainCityInfoId(domainCityInfoId);
                        addressAddDO.setCityId(cityDO.getId());
                        addressAddDO.setCityName(cityDO.getCityName());
                        addressAddDO.setDelFlag(0);
                        addressAddDO.setCreateTime(nowTime);
                        addressAddDOList.add(addressAddDO);
                        continue;
                    }
                    AddressDO addressUpdateDO = new AddressDO();
                    addressUpdateDO.setId(addressAddAndUpdateReqDTO.getAddressId());
                    addressUpdateDO.setAddress(addressAddAndUpdateReqDTO.getAddressName());
                    addressUpdateDO.setDomainCityInfoId(domainCityInfoId);
                    addressUpdateDO.setUpdateTime(nowTime);
                    addressDOMap.put(addressAddAndUpdateReqDTO.getAddressId(),addressUpdateDO);
                }
                if(Objects.isNull(addressDOList)||addressDOList.size()==0) {
                    continue;
                }
                List<Integer> delAddressIdList=new ArrayList<>();
                for(AddressDO addressDO:addressDOList) {
                    if(!addressDOMap.containsKey(addressDO.getId())) {
                        delAddressIdList.add(addressDO.getId());
                        continue;
                    }
                    AddressDO addressUpdateDO = addressDOMap.get(addressDO.getId());
                    if((StringUtils.hasLength(addressUpdateDO.getAddress())&&
                            !addressDO.getAddress().equals(addressUpdateDO.getAddress()))
                            ||!addressDO.getDomainCityInfoId().equals(domainCityInfoId)) {
                        addressService.update(addressUpdateDO);
                    }
                }
                if(delAddressIdList.size()>0){
                    addressService.delByIds(delAddressIdList);
                }

            }
        }
        if(delDomainCityInfoIdList.size()>0){
            domainCityInfoService.delByIds(delDomainCityInfoIdList);
            addressService.delByDomainCityInfoIds(delDomainCityInfoIdList);
        }
        if(addressAddDOList.size()>0) {
            addressService.batchSave(addressAddDOList);
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void addDomain(DomainAddAndUpdateReqDTO requestParam) {
        if(!StringUtils.hasLength(requestParam.getDomainName())){
            throw new ServiceException("执行失败，请输入区域信息!");
        }
        DomainDO domainDO=domainMapper.queryByDomainName(requestParam.getDomainName());
        if(Objects.nonNull(domainDO)){
            throw new ServiceException("执行失败，区域已经存在");
        }
        Date nowTime = new Date();
        DomainDO domainAddDO = new DomainDO();
        domainAddDO.setDomainName(requestParam.getDomainName());
        domainAddDO.setDelFlag(0);
        domainAddDO.setCreateTime(nowTime);
        domainMapper.insert(domainAddDO);

        List<DomainCityInfoAddAndUpdateReqDTO> cityList = requestParam.getCityList();
        if(Objects.isNull(cityList)||cityList.size()==0){
            return;
        }
        Map<Integer,DomainCityInfoAddAndUpdateReqDTO> domainCityInfoAddAndUpdateReqDTOMap=new HashMap<>();
        for(DomainCityInfoAddAndUpdateReqDTO item:cityList){
            if(!domainCityInfoAddAndUpdateReqDTOMap.containsKey(item.getCityId())){
                domainCityInfoAddAndUpdateReqDTOMap.put(item.getCityId(),item);
            }else{
                DomainCityInfoAddAndUpdateReqDTO domainCItyDTO = domainCityInfoAddAndUpdateReqDTOMap.get(item.getCityId());
                List<AddressAddAndUpdateReqDTO> addressList1 = domainCItyDTO.getAddressList();
                List<AddressAddAndUpdateReqDTO> addressList2 = item.getAddressList();
                if(Objects.isNull(addressList1)&&Objects.isNull(addressList2)){
                    continue;
                }
                if(Objects.isNull(addressList1)){
                    domainCItyDTO.setAddressList(addressList1);
                    continue;
                }
                if(Objects.isNull(addressList2)){
                    domainCItyDTO.setAddressList(addressList2);
                    continue;
                }
                addressList1.addAll(addressList2);
                domainCItyDTO.setAddressList(addressList1);
            }
        }
        Set<Integer> domainCityInfoKeySet = domainCityInfoAddAndUpdateReqDTOMap.keySet();
        List<DomainCityInfoAddAndUpdateReqDTO> addressAddAndUpdateReqDTOList=new ArrayList<>();
        for(Integer key:domainCityInfoKeySet){
            addressAddAndUpdateReqDTOList.add(domainCityInfoAddAndUpdateReqDTOMap.get(key));
        }
        List<AddressDO> addressAddDOList=new ArrayList<>();
        for (DomainCityInfoAddAndUpdateReqDTO cityAddReqDTO:addressAddAndUpdateReqDTOList){
            CityDO cityQueryDO=new CityDO();
            cityQueryDO.setId(cityAddReqDTO.getCityId());
            cityQueryDO.setDelFlag(0);
            CityDO cityDO = cityService.queryById(cityQueryDO);
            if(Objects.isNull(cityDO)){
                throw new ServiceException("执行失败，城市信息错误");
            }
            DomainCityInfoDO domainCityInfoAddDO = new DomainCityInfoDO();
            domainCityInfoAddDO.setDomainName(requestParam.getDomainName());
            domainCityInfoAddDO.setDomainId(domainAddDO.getId());
            domainCityInfoAddDO.setCityId(cityDO.getId());
            domainCityInfoAddDO.setCityName(cityDO.getCityName());
            domainCityInfoAddDO.setCreateTime(nowTime);
            domainCityInfoAddDO.setDelFlag(0);
            domainCityInfoService.save(domainCityInfoAddDO);
            List<AddressAddAndUpdateReqDTO> addressList = cityAddReqDTO.getAddressList();
            if(Objects.isNull(addressList)||addressList.size()==0){
                continue;
            }
            for(AddressAddAndUpdateReqDTO address:addressList){
                if(!StringUtils.hasLength(address.getAddressName())){
                    continue;
                }
                AddressDO addressAddDO=new AddressDO();
                addressAddDO.setAddress(address.getAddressName());
                addressAddDO.setDelFlag(0);
                addressAddDO.setDomainId(domainAddDO.getId());
                addressAddDO.setDomainCityInfoId(domainCityInfoAddDO.getId());
                addressAddDO.setCityId(cityDO.getId());
                addressAddDO.setCityName(cityDO.getCityName());
                addressAddDO.setCreateTime(nowTime);
                addressAddDOList.add(addressAddDO);
            }
        }
        if(addressAddDOList.size()>0){
            addressService.batchSave(addressAddDOList);
        }
    }

    @Override
    public void deleteDomain(Integer id) {
        domainMapper.delete(id);
        domainCityInfoService.deleteByDomainId(id);
        addressService.deleteByDomainId(id);
    }
}
