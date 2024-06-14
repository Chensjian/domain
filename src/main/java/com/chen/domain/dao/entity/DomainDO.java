package com.chen.domain.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainDO extends BaseDO {
    /**
     * 区域id
     */
    private Integer id;
    /**
     * 区域名称
     */
    private String domainName;
}
