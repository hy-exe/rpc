package com.rpc.common.tcp.codec.hessian;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.rpc.common.tcp.codec.RpcSerialize;

/**
 * @author yin.huang
 * @date 2018年3月20日 下午2:12:23
 */
public class HessianSerialize implements RpcSerialize {

  public void serialize(OutputStream output, Object object) {
    Hessian2Output ho = new Hessian2Output(output);
    try {
      ho.startMessage();
      ho.writeObject(object);
      ho.completeMessage();
      ho.close();
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Object deserialize(InputStream input) {
    Object result = null;
    try {
      Hessian2Input hi = new Hessian2Input(input);
      hi.startMessage();
      result = hi.readObject();
      hi.completeMessage();
      hi.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

}
