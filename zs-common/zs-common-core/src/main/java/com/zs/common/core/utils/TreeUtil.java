package com.zs.common.core.utils;

import java.util.*;

/**
 * @author 86740
 */

public class TreeUtil {

    /**
     * 根据pid，构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long pid) {
        List<T> treeList = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (pid.equals(treeNode.getPid())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
        for (T treeNode : treeNodes) {
            if (rootNode.getId().equals(treeNode.getPid())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();

        Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        treeNodes.forEach(node -> nodeMap.computeIfAbsent(node.getId(), k -> node));

        nodeMap.values().forEach(node -> {
            T parent = nodeMap.get(node.getPid());
            if (parent != null && !Objects.equals(node.getId(), parent.getId())) {
                parent.getChildren().add(node);
            } else {
                result.add(node);
            }
        });

        return result;
    }


    public static <T extends TreeNode<T>> List<T> getTreeParentEntity(List<T> elements, Long id, List<T> newList) {

        //找到父节点
        Optional<T> first = elements.stream().filter(
                e -> Objects.equals(e.getId(), id)
        ).findFirst();

        //已经找到最顶层
        if (first.isEmpty()) {
            return newList;
        }

        T t = first.get();
        newList.add(t);
        //递归遍历父级
        return getTreeParentEntity(elements, t.getPid(), newList);
    }

}
