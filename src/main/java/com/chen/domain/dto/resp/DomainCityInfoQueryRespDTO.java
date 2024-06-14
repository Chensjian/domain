package com.chen.domain.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainCityInfoQueryRespDTO {
    /**
     * 区域城市id
     */
    private Integer domainCityInfoId;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 区域id
     */
    private Integer domainId;

    /**
     * 区域名称
     */
    private String domainName;

    private List<AddressQueryRespDTO> addressList;
}
