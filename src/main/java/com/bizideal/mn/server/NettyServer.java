package com.bizideal.mn.server;

import com.bizideal.mn.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 10:12
 * @version: 1.0
 * @Description:
 */
public class NettyServer {

    private int port = 9688;

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port)).childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpServerCodec()); //请求解码器
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new IdleStateHandler(60, 0, 0));
                            ch.pipeline().addLast(new MessageHandler());
                        }

                        ;

                    }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = sbs.bind(port).sync();

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().start();
    }
}

