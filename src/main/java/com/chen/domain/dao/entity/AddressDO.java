package com.chen.domain.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDO extends BaseDO {
    /**
     * 地址id
     */
    private Integer id;
    /**
     * 地址
     */
    private String address;
    /**
     * 区域id
     */
    private Integer domainId;
    /**
     * 区域城市信息id
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
}
