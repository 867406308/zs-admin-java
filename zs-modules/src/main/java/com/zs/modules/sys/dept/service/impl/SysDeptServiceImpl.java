package com.zs.modules.sys.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.core.HttpEnum;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.utils.MyTreeNode;
import com.zs.common.core.utils.TreeUtil;
import com.zs.common.mp.service.MpSysDeptService;
import com.zs.modules.sys.dept.domain.entity.SysDeptEntity;
import com.zs.modules.sys.dept.domain.params.SysDeptAddParams;
import com.zs.modules.sys.dept.domain.params.SysDeptQueryParams;
import com.zs.modules.sys.dept.domain.vo.SysDeptVo;
import com.zs.modules.sys.dept.mapper.SysDeptMapper;
import com.zs.modules.sys.dept.service.ISysDeptService;
import com.zs.modules.sys.post.domain.params.SysPostQueryParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;
import com.zs.modules.sys.post.service.ISysPostService;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 86740
 */

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements ISysDeptService, MpSysDeptService {

    @Resource
    @Lazy
    private ISysPostService sysPostService;

    @NotNull
    @Override
    public List<SysDeptVo> getTree(SysDeptQueryParams sysDeptQueryParams) {

        List<SysDeptEntity> entityList = baseMapper.getList(sysDeptQueryParams);
        List<SysDeptVo> list = BeanUtil.copyToList(entityList, SysDeptVo.class);
        return TreeUtil.build(list);


    }

    @Nullable
    @Override
    public List<SysDeptVo> getList(SysDeptQueryParams sysDeptQueryParams) {
        List<SysDeptEntity> entityList = baseMapper.getList(sysDeptQueryParams);
        return BeanUtil.copyToList(entityList, SysDeptVo.class);
    }


    @Override
    public void save(@NotNull SysDeptAddParams sysOrgAddParams) {
        SysDeptEntity sysDeptEntity = BeanUtil.copyProperties(sysOrgAddParams, SysDeptEntity.class);
        sysDeptEntity.setPids(StrUtil.join(",", getTree(sysOrgAddParams)));
        baseMapper.insert(sysDeptEntity);
    }

    @NotNull
    public List<Long> getTree(@NotNull SysDeptAddParams sysOrgAddParams) {
        List<SysDeptEntity> deptList = baseMapper.selectList(new QueryWrapper<>());
        Map<Long, SysDeptEntity> map = new HashMap<>(deptList.size());
        for (SysDeptEntity entity : deptList) {
            map.put(entity.getSysDeptId(), entity);
        }
        List<Long> pidList = new ArrayList<>();
        getPid(sysOrgAddParams.getPid(), map, pidList);
        return pidList;
    }

    public void getPid(Long pid, @NotNull Map<Long, SysDeptEntity> map, @NotNull List<Long> pidList) {
        SysDeptEntity parent = map.get(pid);
        if (Objects.nonNull(parent)) {
            pidList.add(parent.getSysDeptId());
            getPid(parent.getPid(), map, pidList);
        }
    }


    @Override
    public void update(SysDeptAddParams sysOrgAddParams) {
        baseMapper.updateById(BeanUtil.copyProperties(sysOrgAddParams, SysDeptEntity.class));
    }

    @Override
    public SysDeptVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.getBySysDeptId(id), SysDeptVo.class);
    }

    @Override
    public void removeById(Long sysDeptId) {
        // 查询用户是否绑定部门
        // 查询是否存在子级
        Long count = baseMapper.selectCount(new QueryWrapper<SysDeptEntity>().eq("pid", sysDeptId));
        if (count > 0) {
            throw new ZsException(HttpEnum.SUB_DEPT_ERROR);
        }
        baseMapper.deleteById(sysDeptId);
    }

    @NotNull
    @Override
    public List<Long> getSubDeptIdList(Long sysDeptId) {

        if (Objects.isNull(sysDeptId)) {
            return new ArrayList<>();
        }
        List<Long> deptIdList = baseMapper.getSubDeptIdList(sysDeptId);
        deptIdList.add(sysDeptId);

        return deptIdList;
    }

    @Override
    public String getBySysDeptId(Long sysDeptId) {
        SysDeptEntity sysDeptEntity = baseMapper.getBySysDeptId(sysDeptId);
        if (Objects.isNull(sysDeptEntity)) {
            return "";
        }
        // 将pids转换成list数组
        String[] split = sysDeptEntity.getPids().split(",");
        List<Long> pids = Arrays.stream(split).map(Long::valueOf).toList();

        // 查询父级部门列表
        List<SysDeptEntity> list = baseMapper.selectBatchIds(pids);

        // 构造结果字符串
        String result = list.stream().map(SysDeptEntity::getDeptName).collect(Collectors.joining("/"));
        return result + ("/" + sysDeptEntity.getDeptName());
    }

    @NotNull
    @Override
    public Set<Long> getDeptAndChildrenDeptIds(Long deptId) {
        return new HashSet<>(this.getSubDeptIdList(deptId));
    }

    @Override
    public List<MyTreeNode> getDeptPostTree() {

        List<SysDeptEntity> entityList = baseMapper.getList(new SysDeptQueryParams());
        List<SysDeptVo> list = BeanUtil.copyToList(entityList, SysDeptVo.class);
        List<SysPostVo>  postList = sysPostService.getList(new SysPostQueryParams());

        return buildDeptPostTree(list, Objects.requireNonNull(postList));
    }



    public List<MyTreeNode> buildDeptPostTree(List<SysDeptVo> deptList, List<SysPostVo> postList) {
        // 使用TreeMap来自动排序部门ID
        Map<Long, MyTreeNode> nodeMap = new TreeMap<>();
        List<MyTreeNode> rootList = new ArrayList<>();

        // 创建部门节点，并构建部门树结构
        deptList.stream()
                .map(dept -> new MyTreeNode(dept.getSysDeptId(), dept.getPid(), dept.getDeptName()))
                .forEach(node -> {
                    nodeMap.put(node.getId(), node);
                    if (node.getPid() == null || node.getPid() == 0) {
                        rootList.add(node);
                    } else {
                        MyTreeNode parent = nodeMap.get(node.getPid());
                        if (parent != null) {
                            parent.getChildren().add(node);
                        }
                    }
                });

        // 将岗位添加到对应的部门节点下
        postList.stream()
                .filter(post -> nodeMap.containsKey(post.getSysDeptId()))
                .map(post -> new MyTreeNode(post.getSysPostId(), post.getSysDeptId(), post.getPostName()))
                .forEach(postNode -> nodeMap.get(postNode.getPid()).getChildren().add(postNode));

        return rootList;
    }


}
