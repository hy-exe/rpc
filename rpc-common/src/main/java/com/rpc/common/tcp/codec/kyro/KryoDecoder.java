package com.rpc.common.tcp.codec.kyro;

import com.rpc.common.tcp.codec.MessageCodecUtil;
import com.rpc.common.tcp.codec.MessageDecoder;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午2:11:04
 */ 
public class KryoDecoder extends MessageDecoder {

  public KryoDecoder(MessageCodecUtil util) {
    super(util);
  }
}
