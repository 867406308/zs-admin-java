package com.zs.sys.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.sys.notice.domain.entity.SysNoticeDetailsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 86740
 */
@Mapper
public interface SysNoticeDetailsMapper extends BaseMapper<SysNoticeDetailsEntity> {

    List<SysNoticeDetailsEntity> list(Long sysNoticeId);
}
