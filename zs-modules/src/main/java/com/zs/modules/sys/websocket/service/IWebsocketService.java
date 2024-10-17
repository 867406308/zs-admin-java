package com.zs.modules.sys.websocket.service;


import com.zs.common.websocket.utils.UserTypeEnum;
import com.zs.modules.sys.user.domain.vo.SysUserVo;

import java.util.List;

public interface IWebsocketService {

    /** 获取在线用户 **/
    List<SysUserVo> getOnlineUserList(UserTypeEnum userType);

}
