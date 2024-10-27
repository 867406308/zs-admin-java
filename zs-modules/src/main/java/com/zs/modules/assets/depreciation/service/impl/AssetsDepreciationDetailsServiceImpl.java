package com.zs.modules.assets.depreciation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.model.domain.SysDeptDTO;
import com.zs.common.core.model.domain.SysDictDataDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.core.utils.DictRedisUtil;
import com.zs.modules.assets.depreciation.domain.entity.AssetsDepreciationDetailsEntity;
import com.zs.modules.assets.depreciation.domain.params.AssetsDepreciationDetailsQueryParams;
import com.zs.modules.assets.depreciation.domain.vo.AssetsDepreciationDetailsVo;
import com.zs.modules.assets.depreciation.mapper.AssetsDepreciationDetailsMapper;
import com.zs.modules.assets.depreciation.service.IAssetsDepreciationDetailsService;
import com.zs.modules.sys.dept.service.ISysDeptService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @NotNull
    @Override
    public PageResult<AssetsDepreciationDetailsVo> page(@NotNull AssetsDepreciationDetailsQueryParams assetsDepreciationDetailsQueryParams) {
        Page<AssetsDepreciationDetailsEntity> page = new PageInfo<>(assetsDepreciationDetailsQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(assetsDepreciationDetailsQueryParams);
        IPage<AssetsDepreciationDetailsEntity> iPage = baseMapper.page(page, params);
        List<AssetsDepreciationDetailsVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsDepreciationDetailsVo.class);
        updateItemsWithDetails(list);

        return new PageResult<>(list, page.getTotal(), AssetsDepreciationDetailsVo.class);
    }

    private void updateItemsWithDetails(@NotNull List<AssetsDepreciationDetailsVo> list) {
        updateItemsWithDictLabels(list);
        updateItemsWithDept(list);
    }

    /** 资产状态 **/
    private void updateItemsWithDictLabels(@NotNull List<AssetsDepreciationDetailsVo> list) {
        List<String> dictKeys = List.of(RedisConstants.SYS_DICT_KEY + "depreciationCode");
        // 获取字典使用状态
        List<SysDictDataDTO> sysDictDataDTOList = DictRedisUtil.getMulti(dictKeys);
        Map<String, SysDictDataDTO> dictMap = sysDictDataDTOList.stream().collect(Collectors.toMap(SysDictDataDTO::getDictValue, Function.identity()));
        list.forEach(item -> Optional.ofNullable(dictMap.get(item.getDepreciationCode())).ifPresent(depreciationCodeDict -> item.setDepreciationCodeDictLabel(depreciationCodeDict.getDictLabel())));
    }

    /** 管理部门名称、使用部门名称 **/
    private void updateItemsWithDept(@NotNull List<AssetsDepreciationDetailsVo> list) {
        List<SysDeptDTO> sysDeptDTOList = BeanUtil.copyToList(iSysDeptService.list(), SysDeptDTO.class);
        Map<Long, SysDeptDTO> deptMap = sysDeptDTOList.stream().collect(Collectors.toMap(SysDeptDTO::getSysDeptId, Function.identity()));
        list.forEach(item -> {
            item.setManageOrgName(deptMap.getOrDefault(item.getManageOrgId(), new SysDeptDTO()).getDeptName());
            item.setUseOrgName(deptMap.getOrDefault(item.getUseOrgId(), new SysDeptDTO()).getDeptName());
        });
    }
}
