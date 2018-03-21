package com.rpc.common.tcp.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午1:57:02
 */
public class MessageEncoder extends MessageToByteEncoder<Object> {

  private MessageCodecUtil util = null;

  public MessageEncoder(final MessageCodecUtil util) {
    this.util = util;
  }

  protected void encode(final ChannelHandlerContext ctx, final Object msg, final ByteBuf out) throws Exception {
    util.encode(out, msg);
  }

}
