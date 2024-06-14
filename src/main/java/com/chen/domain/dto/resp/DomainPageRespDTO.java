package com.chen.domain.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainPageRespDTO {
    /**
     * 区域id
     */
    private Integer domainId;
    /**
     * 区域名称
     */
    private String domainName;
    /**
     * 城市
     */
    private List<String> cityNameList;

    private List<String> addressList;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
}
