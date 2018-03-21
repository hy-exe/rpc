package com.rpc.common.tcp.service;

import java.util.concurrent.TimeUnit;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午2:11:56
 */
public interface Sender {

  void send(Object bean);

  void send(Object bean, Receiver receiver);

  Object sendAndWait(Object bean);

  Object sendAndWait(Object bean, long timeout, TimeUnit timeUnit);

}
