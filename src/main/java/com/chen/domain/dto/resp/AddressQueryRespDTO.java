package com.chen.domain.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressQueryRespDTO {
    /**
     * 地址id
     */
    private Integer addressId;
    /**
     * 地址
     */
    private String addressName;
    /**
     * 城市id
     */
    private Integer domainCityInfoId;
    /**
     * 城市名称
     */
    private String cityName;
}
