package com.zs.common.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 86740
 */
@Data
public class TreeNode<T> implements Serializable {

    private Long id;

    private Long pid;

    private List<T> children = new ArrayList<>();
}
