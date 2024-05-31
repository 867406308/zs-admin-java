package com.zs.assets.depreciation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.depreciation.domain.entity.AssetsDepreciationDetailsEntity;
import com.zs.assets.depreciation.domain.params.AssetsDepreciationDetailsQueryParams;
import com.zs.assets.depreciation.domain.vo.AssetsDepreciationDetailsVo;
import com.zs.assets.depreciation.mapper.AssetsDepreciationDetailsMapper;
import com.zs.assets.depreciation.service.IAssetsDepreciationDetailsService;
import com.zs.assets.info.domain.entity.AssetsInfoEntity;
import com.zs.assets.info.domain.vo.AssetsInfoVo;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.model.domain.SysDeptDTO;
import com.zs.common.core.model.domain.SysDictDataDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.redis.utils.DictRedisUtil;
import com.zs.sys.dept.service.ISysDeptService;
import com.zs.sys.user.service.ISysUserService;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 资产折旧明细
 *
 * @author 86740
 */
@Service
public class AssetsDepreciationDetailsServiceImpl extends ServiceImpl<AssetsDepreciationDetailsMapper, AssetsDepreciationDetailsEntity> implements IAssetsDepreciationDetailsService {

    @Resource
    private ISysDeptService iSysDeptService;

    @Override
    public PageResult<AssetsDepreciationDetailsVo> page(AssetsDepreciationDetailsQueryParams assetsDepreciationDetailsQueryParams) {
        Page<AssetsDepreciationDetailsEntity> page = new PageInfo<>(assetsDepreciationDetailsQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(assetsDepreciationDetailsQueryParams);
        IPage<AssetsDepreciationDetailsEntity> iPage = baseMapper.page(page, params);
        List<AssetsDepreciationDetailsVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsDepreciationDetailsVo.class);
        updateItemsWithDetails(list);

        return new PageResult<>(list, page.getTotal(), AssetsDepreciationDetailsVo.class);
    }

    private void updateItemsWithDetails(List<AssetsDepreciationDetailsVo> list) {
        updateItemsWithDictLabels(list);
        updateItemsWithDept(list);
    }

    /** 资产状态 **/
    private void updateItemsWithDictLabels(List<AssetsDepreciationDetailsVo> list) {
        List<String> dictKeys = List.of(RedisConstants.SYS_DICT_KEY + "depreciationCode");
        // 获取字典使用状态
        List<SysDictDataDTO> sysDictDataDTOList = DictRedisUtil.getMulti(dictKeys);
        Map<String, SysDictDataDTO> dictMap = sysDictDataDTOList.stream().collect(Collectors.toMap(SysDictDataDTO::getDictValue, Function.identity()));
        list.forEach(item -> {
            Optional.ofNullable(dictMap.get(item.getDepreciationCode())).ifPresent(depreciationCodeDict -> item.setDepreciationCodeDictLabel(depreciationCodeDict.getDictLabel()));
        });
    }

    /** 管理部门名称、使用部门名称 **/
    private void updateItemsWithDept(List<AssetsDepreciationDetailsVo> list) {
        List<SysDeptDTO> sysDeptDTOList = BeanUtil.copyToList(iSysDeptService.list(), SysDeptDTO.class);
        Map<Long, SysDeptDTO> deptMap = sysDeptDTOList.stream().collect(Collectors.toMap(SysDeptDTO::getSysDeptId, Function.identity()));
        list.forEach(item -> {
            item.setManageOrgName(deptMap.getOrDefault(item.getManageOrgId(), new SysDeptDTO()).getDeptName());
            item.setUseOrgName(deptMap.getOrDefault(item.getUseOrgId(), new SysDeptDTO()).getDeptName());
        });
    }
}
