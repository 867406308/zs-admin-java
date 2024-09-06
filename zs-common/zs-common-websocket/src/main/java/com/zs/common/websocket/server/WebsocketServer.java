package com.zs.common.websocket.server;

import com.zs.common.websocket.utils.UserTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 86740
 */
@Component
@ServerEndpoint("/websocket/{userId}/{type}")
public class WebsocketServer {


    /**
     * 以用户的userId为key，WebSocket为对象保存起来
     */
    private static final Map<String, WebsocketServer> pcClients = new ConcurrentHashMap<String, WebsocketServer>();
    /**
     * B端用户
     */
    private static final Map<String, WebsocketServer> bClients = new ConcurrentHashMap<String, WebsocketServer>();
    /**
     * PC端在线人数
     */
    public static int pcOnlineCount = 0;
    /**
     * B端在线人数
     */
    public static int bOnlineCount = 0;
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户类型
     */
    private UserTypeEnum userType;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId, @NotNull @PathParam("type") String type) {
        this.session = session;
        this.userId = userId;
        this.userType = UserTypeEnum.valueOf(type.toUpperCase());
        addClients();
    }

    public void addClients() {
        Map<String, WebsocketServer> clients = this.userType == UserTypeEnum.PC ? pcClients : bClients;
        clients.putIfAbsent(userId, this);
        addOnlineCount(userType);
    }

    public void addOnlineCount(UserTypeEnum type)  {
        if (type == UserTypeEnum.PC) {
            pcOnlineCount++;
        } else if (type == UserTypeEnum.APP) {
            bOnlineCount++;
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("连接关闭" + session);
        if (pcClients.containsKey(userId)) {
            pcClients.remove(userId);
            pcOnlineCount--;
        }
        if (bClients.containsKey(userId)) {
            bClients.remove(userId);
            bOnlineCount--;
        }
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {

    }

    /**
     * 发送全部自定义消息
     */
    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 发送个人自定义消息
     */

    public void sendMessage(String userId, String message) {
        getUserClient(userId).ifPresent(client -> client.sendMessage(message));
    }

    @NotNull
    private Optional<WebsocketServer> getUserClient(String userId) {
        return Optional.ofNullable(pcClients.getOrDefault(userId, bClients.get(userId)));
    }


    /**
     * 发送多人自定义消息
     */
    public void sendMessage(@NotNull List<String> userIds, String message) {
        userIds.forEach(userId -> getUserClient(userId).ifPresent(client -> client.sendMessage(message)));
    }

    /**
     * 获取在线用户
     */
    @NotNull
    public List<String> getOnlineUserList(UserTypeEnum type) {
        return type == UserTypeEnum.PC ? pcClients.keySet().stream().toList() : bClients.keySet().stream().toList();
    }
}
