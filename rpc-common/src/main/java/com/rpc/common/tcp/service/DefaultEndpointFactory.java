package com.rpc.common.tcp.service;

import io.netty.channel.Channel;

/**
 * @author yin.huang
 * @date 2018年3月20日 上午10:30:54
 */
public class DefaultEndpointFactory implements EndpointFactory {

  private Receiver receiver = null;

  @Override
  public Endpoint createEndpoint(Channel channel) {
    DefaultEndpoint endpoint = new DefaultEndpoint();
    endpoint.setChannel(channel);

    endpoint.setReceiver(this.receiver);

    return endpoint;
  }

  @Override
  public void setReceiver(Receiver receiver) {
    this.receiver = receiver;

  }

  public Receiver getReceiver() {
    return receiver;
  }

}
