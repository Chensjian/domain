package com.chen.domain.service;

import com.chen.domain.dto.req.DomainAddAndUpdateReqDTO;
import com.chen.domain.dto.req.DomainPageReqDTO;
import com.chen.domain.dto.req.DomainUpdateReqDTO;
import com.chen.domain.dto.resp.DomainPageRespDTO;
import com.chen.domain.dto.resp.DomainQueryRespDTO;
import com.github.pagehelper.PageInfo;

public interface DomainService {
    PageInfo<DomainPageRespDTO> pageList(DomainPageReqDTO requestParam);

    DomainQueryRespDTO getDomainById(Integer id);

    void updateDomain(DomainAddAndUpdateReqDTO requestParam);

    void addDomain(DomainAddAndUpdateReqDTO requestParam);

    void deleteDomain(Integer id);
}
