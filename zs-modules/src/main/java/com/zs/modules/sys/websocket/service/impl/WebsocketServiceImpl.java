package com.zs.modules.sys.websocket.service.impl;

import com.zs.common.websocket.server.WebsocketServer;
import com.zs.common.websocket.utils.UserTypeEnum;
import com.zs.modules.sys.user.domain.vo.SysUserVo;
import com.zs.modules.sys.websocket.service.IWebsocketService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsocketServiceImpl implements IWebsocketService {

    @Resource
    private WebsocketServer websocketService;

    @NotNull
    @Override
    public List<SysUserVo> getOnlineUserList(UserTypeEnum userType) {
        List<String> userIds = websocketService.getOnlineUserList(userType);
        return List.of();
    }
}
