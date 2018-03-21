package com.rpc.common.tcp.codec;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午1:55:47
 */
public interface MessageCodecUtil {

  // RPC消息报文头长度4个字节
  final public static int MESSAGE_LENGTH = 4;

  public void encode(final ByteBuf out, final Object message) throws IOException;

  public Object decode(byte[] body) throws IOException;
}
