package com.rpc.common.tcp.codec.hessian;

import com.rpc.common.tcp.codec.MessageCodecUtil;
import com.rpc.common.tcp.codec.MessageDecoder;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午2:17:04
 */
public class HessianDecoder extends MessageDecoder {

  public HessianDecoder(MessageCodecUtil util) {
    super(util);
  }
}
