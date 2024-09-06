package com.zs.common.mp.handler;


import cn.hutool.extra.spring.SpringUtil;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.utils.SecurityUtil;
import com.zs.common.mp.annotation.DataScope;
import com.zs.common.mp.service.MpSysDeptService;
import com.zs.common.mp.service.MpSysRoleService;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MyDataPermissionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyDataPermissionHandler.class);

    private  MpSysRoleService mpSysRoleService;
    private  MpSysDeptService mpSysDeptService;


    /**
     * 根据SQL语句和映射语句ID生成数据权限SQL片段
     * @param plainSelect SQL语句
     * @param mappedStatementId 映射语句ID
     * @return 数据权限SQL表达式
     */
    @Nullable
    public Expression getSqlSegment(@NotNull PlainSelect plainSelect, @NotNull String mappedStatementId) {
        // 待执行 SQL Where 条件表达式
        Expression where = plainSelect.getWhere();
        if (where == null) {
            return null;
        }
        DataScope dataScope = this.getDataScope(mappedStatementId);
        if (dataScope == null) {
            return where;
        }
        return doFilter(plainSelect, where);
    }



    /**
     * 执行数据权限过滤
     * @param plainSelect SQL语句
     * @param where 原始WHERE条件
     * @return 过滤后的WHERE条件
     */
    @Nullable
    public Expression doFilter(@NotNull PlainSelect plainSelect, @Nullable Expression where) {
         mpSysRoleService = SpringUtil.getBean(MpSysRoleService.class);
         mpSysDeptService = SpringUtil.getBean(MpSysDeptService.class);
        Table table = (Table) plainSelect.getFromItem();
        String aliasName = Optional.ofNullable(table.getAlias()).map(Alias::getName).orElse(table.getName());

        // 获取当前登录用户信息
        LoginUserInfo loginUserInfo = SecurityUtil.getUserInfo();

        // 判断是否为管理员
        if (loginUserInfo.getSysUser().getIsAdmin() == 1) {
            return where;
        }

        Set<Integer> dataScopes = mpSysRoleService.getDataScope(loginUserInfo.getSysUser().getSysUserId());

        List<Expression> expressions = dataScopes.stream()
                .filter(scope -> scope != 1) // 跳过所有权限
                .map(scope -> createExpressionForScope(scope, aliasName, loginUserInfo, mpSysRoleService, mpSysDeptService))
                .filter(Objects::nonNull)
                .toList();

        Expression dataScopesExpression = expressions.stream().reduce(OrExpression::new).orElse(null);

        return where == null ? dataScopesExpression : new AndExpression(where, new Parenthesis(dataScopesExpression));

    }

    /**
     * 创建特定数据范围的SQL表达式
     * @param dataScope 数据范围类型
     * @param aliasName 表别名
     * @param loginUserInfo 登录用户信息
     * @return SQL表达式
     */
    @Nullable
    private Expression createExpressionForScope(int dataScope, String aliasName, @NotNull LoginUserInfo loginUserInfo, @NotNull MpSysRoleService roleService, @NotNull MpSysDeptService deptService) {
        return switch (dataScope) {
            case 2 -> createCustomDeptPermissionExpression(roleService, loginUserInfo, aliasName);
            case 3 -> createOwnDeptPermissionExpression(loginUserInfo, aliasName);
            case 4 -> createDeptWithChildrenPermissionExpression(deptService, loginUserInfo, aliasName);
            case 5 -> createCreatedByUserPermissionExpression(loginUserInfo, aliasName);
            default -> null;
        };
    }

    // 自定义部门权限表达式
    @Nullable
    private Expression createCustomDeptPermissionExpression(@NotNull MpSysRoleService roleService, @NotNull LoginUserInfo loginUserInfo, String aliasName) {
        Set<Long> deptIds = roleService.getRoleDeptIds(loginUserInfo.getSysUser().getSysUserId());
        if (deptIds.isEmpty()) {
            return null;
        }
        ExpressionList<Expression> deptIdExpressions = deptIds.stream()
                .map(LongValue::new)
                .collect(Collectors.toCollection(ExpressionList::new));
        return new InExpression(new Column(aliasName + ".create_dept_id"), new Parenthesis(deptIdExpressions));
    }

    // 自己部门权限表达式
    @NotNull
    private Expression createOwnDeptPermissionExpression(@NotNull LoginUserInfo loginUserInfo, String aliasName) {
        return new EqualsTo(
                new Column(aliasName + ".create_dept_id"),
                new LongValue(loginUserInfo.getSysUser().getSysDeptId())
        );
    }

    // 部门及子部门权限表达式
    @Nullable
    private Expression createDeptWithChildrenPermissionExpression(@NotNull MpSysDeptService deptService, @NotNull LoginUserInfo loginUserInfo, String aliasName) {
        Set<Long> deptIdsWithChildren = deptService.getDeptAndChildrenDeptIds(loginUserInfo.getSysUser().getSysDeptId());
        if (deptIdsWithChildren.isEmpty()) {
            return null;
        }

        ExpressionList<Expression> deptIdExpressions = deptIdsWithChildren.stream()
                .map(LongValue::new)
                .collect(Collectors.toCollection(ExpressionList::new));
        return new InExpression(new Column(aliasName + ".create_dept_id"), new Parenthesis(deptIdExpressions));
    }

    // 本人权限表达式
    @NotNull
    private Expression createCreatedByUserPermissionExpression(@NotNull LoginUserInfo loginUserInfo, String aliasName) {
        return new EqualsTo(
                new Column(aliasName + ".creator"),
                new LongValue(loginUserInfo.getSysUser().getSysUserId())
        );
    }


    /**
     * 从类和方法中获取数据范围注解
     * @param mappedStatementId 映射语句ID
     * @return 数据范围注解
     */
    @Nullable
    private DataScope getDataScope(@NotNull String mappedStatementId) {
        String className = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);

        try {
            Class<?> clazz = Class.forName(className);
            Method method = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> m.getName().equals(methodName))
                    .findFirst()
                    .orElse(null);
            return method != null ? method.getAnnotation(DataScope.class) : null;
        } catch (ClassNotFoundException e) {
            logger.error("获取mapper类失败", e);
        }
        return null;
    }

}
