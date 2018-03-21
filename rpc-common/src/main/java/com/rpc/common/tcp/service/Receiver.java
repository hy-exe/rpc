package com.rpc.common.tcp.service;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午3:42:18
 */
public interface Receiver {

  void messageReceived(Object msg);
}
