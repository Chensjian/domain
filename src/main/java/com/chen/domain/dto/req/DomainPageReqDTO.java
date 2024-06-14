package com.chen.domain.dto.req;

import com.chen.domain.common.page.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DomainPageReqDTO extends Page {
    /**
     * 区域名
     */
    private String domainName;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 地址
     */
    private String address;

    /**
     * 创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String beginTime;

    /**
     * 创建结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String endTime;

    /**
     * 删除标识
     */
    private Integer delFlag;
}
