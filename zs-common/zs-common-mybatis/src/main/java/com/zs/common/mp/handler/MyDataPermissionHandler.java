package com.zs.common.mp.handler;


import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.utils.SecurityUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class MyDataPermissionHandler implements DataPermissionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyDataPermissionHandler.class);

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        return null;
    }


//    @Override
//    public Expression getSqlSegment(Expression where, String mappedStatementId) {
//        try {
//            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
//            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
//            Method[] methods = clazz.getDeclaredMethods();
//            for (Method method : methods) {
//                DataPermission annotation = method.getAnnotation(DataPermission.class);
//                if (ObjectUtils.isNotEmpty(annotation) && (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName))) {
//                    // 获取当前的用户
//                    LoginUserInfo loginUser = SecurityUtil.getUserInfo();
//                    if (ObjectUtils.isNotEmpty(loginUser) && ObjectUtils.isNotEmpty(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
//                        return dataScopeFilter(loginUser.getUser(), annotation.value(), where);
//                    }
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return where;
//    }
}
