package com.rpc.common.tcp.codec.kyro;

import com.rpc.common.tcp.codec.MessageCodecUtil;
import com.rpc.common.tcp.codec.MessageEncoder;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午2:11:33
 */
public class KryoEncoder extends MessageEncoder {

  public KryoEncoder(MessageCodecUtil util) {
    super(util);
  }
}