package com.rpc.common.tcp.service;

import com.rpc.common.tcp.domain.IpPortPair;

import io.netty.channel.Channel;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午4:21:09
 */
public interface Endpoint extends Sender, Receiver {

  void stop();

  void setChannel(Channel channel);

  void setReceiver(Receiver receiver);

  IpPortPair getRemoteAddress();

}
