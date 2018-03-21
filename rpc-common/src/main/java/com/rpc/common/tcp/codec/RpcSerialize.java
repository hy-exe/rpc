package com.rpc.common.tcp.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午1:55:18
 */
public interface RpcSerialize {

  void serialize(OutputStream output, Object object) throws IOException;

  Object deserialize(InputStream input) throws IOException;
}
