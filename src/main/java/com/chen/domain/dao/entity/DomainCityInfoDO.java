package com.chen.domain.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 城市实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainCityInfoDO extends BaseDO{
    /**
     * 城市id
     */
    private Integer id;
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
}
