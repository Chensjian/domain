package com.chen.domain.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class DomainCityInfoAddAndUpdateReqDTO {
    private Integer domainCityInfoId;
    private Integer cityId;
    private List<AddressAddAndUpdateReqDTO> addressList;
}
