package com.bizideal.mn.handler;

import com.alibaba.fastjson.JSONObject;
import com.bizideal.mn.entity.Group;
import com.bizideal.mn.entity.UserInfo;
import com.bizideal.mn.mapper.ChannelManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 10:15
 * @version: 1.0
 * @Description:
 */
@ChannelHandler.Sharable
public class MessageHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) { // 这是啥意思啊，老哥！
            handleHttpRequest(ctx, (FullHttpRequest) msg);
            return;
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        // 判断是否关闭链路命令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否Pong消息
        if (frame instanceof PongWebSocketFrame) {
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        // 本程序目前只支持文本消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(frame.getClass().getName() + " frame type not supported");
        }

        String json = (((TextWebSocketFrame) frame)).text();
        System.out.println("receive message : " + json);

        JSONObject parse = JSONObject.parseObject(json);
        Integer type = parse.getInteger("type");
        if (type == null) {
            logger.info("非法请求. msg: {}", msg);
            return;
        }
        if (type == 9) {
            logger.debug("接收到来自：{}心跳包..", ctx.channel().id());
            return;
        }
        Integer userId = parse.getInteger("userId");
        Integer groupId = parse.getInteger("groupId");
        UserInfo userInfo = ChannelManager.userIdUserInfo.get(userId);
        Group group = groupId == null ? null : ChannelManager.groupIdGrups.get(groupId);
        if (type == 0) {    // 用户建立连接
            ChannelManager.addConnection(userId, ctx.channel());
        } else if (type == 1) { // 加入群组
            ChannelManager.addGroupConnection(userId, groupId);
            // 发送欢迎消息
            if (null != groupId) {
                DefaultChannelGroup channels = ChannelManager.groupIdChannel.get(groupId);
                String message = userInfo.getName() + " 进入群组 ";
                for (Channel channel : channels) {
                    channel.writeAndFlush(new TextWebSocketFrame(message));
                }
            }
        } else if (type == 2) { // 发送群消息
            String msg1 = parse.getString("msg");
            DefaultChannelGroup channels = ChannelManager.groupIdChannel.get(groupId);
            String message = userInfo.getName() + " 说: " + msg1;
            for (Channel channel : channels) {
                channel.writeAndFlush(new TextWebSocketFrame(message));
            }
        } else if (type == 3) { // 退出群组
            ChannelManager.removeGroupConnection(userId, groupId);
            // 发送退群消息
            DefaultChannelGroup channels = ChannelManager.groupIdChannel.get(groupId);
            String message = userInfo.getName() + " 退出群组";
            for (Channel channel : channels) {
                channel.writeAndFlush(new TextWebSocketFrame(message));
            }
        }

    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            ctx.channel().close();
            return;
        }
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, true);
        handshaker = handshakerFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 动态加入websocket的编解码处理
            handshaker.handshake(ctx.channel(), request);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}

