package com.chen.domain.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class DomainUpdateReqDTO {
    private String domainName;
    private List<DomainCityInfoUpdateReqDTO> cityList;
}
