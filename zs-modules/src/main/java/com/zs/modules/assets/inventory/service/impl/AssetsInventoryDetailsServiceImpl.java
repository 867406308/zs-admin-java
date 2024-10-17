package com.zs.modules.assets.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.modules.assets.info.service.AssetsInfoService;
import com.zs.modules.assets.inventory.domain.entity.AssetsInventoryDetailsEntity;
import com.zs.modules.assets.inventory.domain.params.AssetsInventoryDetailsQueryParams;
import com.zs.modules.assets.inventory.domain.vo.AssetsInventoryDetailsVo;
import com.zs.modules.assets.inventory.mapper.AssetsInventoryDetailsMapper;
import com.zs.modules.assets.inventory.service.AssetsInventoryDetailsService;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.model.domain.SysDictDataDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.core.utils.DictRedisUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class AssetsInventoryDetailsServiceImpl extends ServiceImpl<AssetsInventoryDetailsMapper, AssetsInventoryDetailsEntity> implements AssetsInventoryDetailsService {


    @Resource
    private AssetsInfoService assetsInfoService;

    @NotNull
    @Override
    public PageResult<AssetsInventoryDetailsVo> page(@NotNull AssetsInventoryDetailsQueryParams assetsInventoryDetailsQueryParams) {
        Page<AssetsInventoryDetailsEntity> page = new PageInfo<>(assetsInventoryDetailsQueryParams);

        Map<String, Object> params = BeanUtil.beanToMap(assetsInventoryDetailsQueryParams);
        params.put("inventoryId", assetsInventoryDetailsQueryParams.getInventoryId());

        IPage<AssetsInventoryDetailsEntity> iPage = baseMapper.page(page, params);
        List<AssetsInventoryDetailsVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsInventoryDetailsVo.class);

        updateItemsWithDictLabels(list);
        return new PageResult<>(list, page.getTotal(), AssetsInventoryDetailsVo.class);
    }

    private void updateItemsWithDictLabels(@NotNull List<AssetsInventoryDetailsVo> list) {
        // 获取字典使用状态
        List<SysDictDataDTO> sysDictDataDTOList = DictRedisUtil.getMulti(List.of(RedisConstants.SYS_DICT_KEY + "assets_status"));
        Map<String, SysDictDataDTO> dictMap = sysDictDataDTOList.stream().collect(Collectors.toMap(SysDictDataDTO::getDictValue, Function.identity()));
        list.forEach(item -> {
            Optional.ofNullable(dictMap.get(item.getAssetsStatusCode())).ifPresent(useStatusDict -> item.setAssetsStatusLabel(useStatusDict.getDictLabel()));
            Optional.ofNullable(dictMap.get(item.getAssetsResultStatusCode())).ifPresent(assetsStatusDict -> item.setAssetsResultStatusCodeLabel(assetsStatusDict.getDictLabel()));
        });
    }
}
