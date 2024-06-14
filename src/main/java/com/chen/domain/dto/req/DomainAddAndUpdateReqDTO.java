package com.chen.domain.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class DomainAddAndUpdateReqDTO {
    private Integer domainId;
    private String domainName;
    private List<DomainCityInfoAddAndUpdateReqDTO> cityList;
}
