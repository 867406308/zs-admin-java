package com.zs.common.core.utils;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

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

    @NotNull
    private List<T> children = new ArrayList<>();
}
