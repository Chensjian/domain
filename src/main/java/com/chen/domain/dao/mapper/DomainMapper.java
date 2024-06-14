package com.chen.domain.dao.mapper;

import com.chen.domain.dao.entity.DomainDO;
import com.chen.domain.dto.req.DomainPageReqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DomainMapper {

    /**
     * 添加区域
     * @param domainDO
     * @return
     */
    int insert(DomainDO domainDO);

    /**
     * 删除区域
     */
    int delete(Integer id);

    /**
     * 分页查询
     * @param requestParam
     * @return
     */
    List<DomainDO> queryList(@Param("requestParam") DomainPageReqDTO requestParam);

    /**
     * 根据id查询区域
     * @param domainQueryDO
     * @return
     */
    DomainDO queryById(@Param("requestParam") DomainDO domainQueryDO);

    int updateById(@Param("requestParam") DomainDO domainUpdateDO);

    DomainDO queryByDomainName(@Param("domainName") String domainName);
}
