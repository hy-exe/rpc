package com.rpc.common.tcp.codec;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午1:57:51
 */
public class MessageDecoder extends ByteToMessageDecoder {

  private static Logger    logger         = LoggerFactory.getLogger(MessageDecoder.class);

  final public static int  MESSAGE_LENGTH = MessageCodecUtil.MESSAGE_LENGTH;

  private MessageCodecUtil util           = null;

  public MessageDecoder(final MessageCodecUtil util) {
    this.util = util;
  }

  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    // 出现粘包导致消息头长度不对，直接返回
    if (in.readableBytes() < MessageDecoder.MESSAGE_LENGTH) {
      return;
    }

    in.markReaderIndex();
    // 读取消息的内容长度
    int messageLength = in.readInt();

    if (messageLength < 0) {
      ctx.close();
    }

    // 读到的消息长度和报文头的已知长度不匹配。那就重置一下ByteBuf读索引的位置
    if (in.readableBytes() < messageLength) {
      in.resetReaderIndex();
      return;
    } else {
      byte[] messageBody = new byte[messageLength];
      in.readBytes(messageBody);

      try {
        Object obj = util.decode(messageBody);
        out.add(obj);
      } catch (IOException ex) {
        logger.warn("when decode msg.happen an exception.e=[{}]", new Object[] { ex.getMessage() });
      }
    }
  }
}
