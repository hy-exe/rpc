package com.rpc.common.tcp.codec;

import io.netty.channel.ChannelPipeline;

/** 
* @author yin.huang 
* @date 2018年3月20日 下午1:56:19 
*/
public interface RpcSerializeFrame {
  public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);
}
