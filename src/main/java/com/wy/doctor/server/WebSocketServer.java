package com.wy.doctor.server;

import com.wy.doctor.handler.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Slf4j
@Component
public class WebSocketServer {

    /**
     * Netty 服务的端口号
     */
    @Value("${websocket.netty.port:19999}")
    public int port;

    @Value("${websocket.netty.path:/websocket}")
    public String webSocketPath;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    /**
     * 启动WebSocket服务器
     */
    private void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        Channel channel = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketServerInitializer(webSocketPath))
                .bind(port)
                .sync()
                .channel();

        log.debug("服务端启动成功，端口号：{}", port);

        channel
                .closeFuture()
                .sync();
    }

    /**
     * 释放资源
     * PreDestroy注解：在容器销毁该组件之前被调用
     * 注解使用前提：该类的实例必须是由容器创建和管理的，如 Spring、JavaEE 容器等。
     */
    @PreDestroy
    public void destroy() {
        if (Objects.nonNull(bossGroup)) {
            bossGroup.shutdownGracefully();
        }

        if (Objects.nonNull(workerGroup)) {
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 初始化WebSocketServer（调用init()）
     * PostConstruct注解：用于指示一个方法在容器创建该组件之后立即调用
     * 注解使用前提：该类的实例必须是由容器创建和管理的，如 Spring、JavaEE 容器等。
     */
    @PostConstruct
    public void init() {
        //这里要新开一个线程，否则会阻塞原本的controller等业务
        new Thread(() -> {
            try {
                start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
