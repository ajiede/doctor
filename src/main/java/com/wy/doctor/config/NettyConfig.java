package com.wy.doctor.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class NettyConfig {

    /**
     * 存储所有在线的客户端Channel
     * -- GETTER --
     *  获取所有在线的客户端Channel
     */
    private static final ChannelGroup onlineChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存储所有在线的UserId与之对应的Channel
     */
    private static final ConcurrentMap<String, Channel> onlineUserChannelMap = new ConcurrentHashMap<>();

    /**
     * 获取所有在线的客户端Channel
     */
    public static ChannelGroup getOnlineChannelGroup() {
        return onlineChannelGroup;
    }

    /**
     * 获取所有在线的UserId与之对应的Channel
     */
    public static ConcurrentMap<String, Channel> getOnlineUserChannelMap() {
        return onlineUserChannelMap;
    }

}
