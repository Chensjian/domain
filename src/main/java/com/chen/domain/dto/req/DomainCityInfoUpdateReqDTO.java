package com.chen.domain.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class DomainCityInfoUpdateReqDTO {
    private Integer cityId;
    private List<Integer> delAddressIdList;
    private List<AddressAddAndUpdateReqDTO> addressList;
}
