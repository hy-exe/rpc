package com.rpc.common.tcp.service;

import io.netty.channel.Channel;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午4:20:32
 */
public interface EndpointFactory {

  Endpoint createEndpoint(Channel channel);

  void setReceiver(Receiver receiver);

}
