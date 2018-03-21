package com.rpc.common.tcp.service;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.domain.AbstractSignal;
import com.rpc.common.tcp.domain.IpPortPair;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午4:24:32
 */
public class DefaultEndpoint implements Endpoint {

  private static final Logger logger      = LoggerFactory.getLogger(DefaultEndpoint.class);

  private Map<Object, Object> contextMap  = new ConcurrentHashMap<Object, Object>();

  private Channel             channel     = null;

  protected Receiver          receiver    = null;

  private int                 waitTimeout = 10000;

  class ResponseFuture<V> extends FutureTask<V> {

    public ResponseFuture() {
      super(new Callable<V>() {

        public V call() throws Exception {
          return null;
        }
      });
    }

    public void set(V v) {
      super.set(v);
    }
  }

  @Override
  public void send(Object bean) {
    if (null != bean) {
      doSend(bean);
    }
  }

  @Override
  public void send(Object bean, Receiver receiver) {
    if (null != bean) {
      Object key = null;
      if (bean instanceof AbstractSignal) {
        key = ((AbstractSignal) bean).getIdentification();
      }
      if (key != null) {
        contextMap.put(key, receiver);
      }
      doSend(bean);
    }
  }

  @Override
  public Object sendAndWait(Object bean) {
    return sendAndWait(bean, waitTimeout, TimeUnit.MILLISECONDS);
  }

  @SuppressWarnings({ "rawtypes" })
  @Override
  public Object sendAndWait(Object bean, long timeout, TimeUnit timeUnit) {
    if (null == bean) {
      return null;
    }

    Object key = ((AbstractSignal) bean).getIdentification();
    ResponseFuture responseFuture = new ResponseFuture();
    contextMap.put(key, responseFuture);

    doSend(bean);
    try {
      return responseFuture.get(timeout, timeUnit);
    } catch (Exception e) {
      logger.info("happen an exception." + e);
      e.printStackTrace();
      return null;
    } finally {
      responseFuture = (ResponseFuture) contextMap.get(key);
      contextMap.remove(key);
      if (responseFuture != null) {
        responseFuture.cancel(false);
      }
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public void messageReceived(Object msg) {

    Object key = null;
    if (msg instanceof AbstractSignal) {
      key = ((AbstractSignal) msg).getIdentification();
    }
    if (key != null) {
      Object context = contextMap.get(key);
      contextMap.remove(key);
      if (null != context) {
        try {
          if (context instanceof ResponseFuture) {
            ((ResponseFuture) context).set(msg);
          }
          if (context instanceof Receiver) {
            ((Receiver) context).messageReceived(msg);
          }
        } catch (Exception e) {
          logger.info("onResponse error. e={}" + e);
        }
      } else {
        if (this.receiver != null) {
          this.receiver.messageReceived(msg);
        }
      }
    }
  }

  @Override
  public void stop() {
    this.receiver = null;
  }

  @Override
  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  @Override
  public void setReceiver(Receiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public IpPortPair getRemoteAddress() {
    InetSocketAddress addr = (InetSocketAddress) channel.remoteAddress();
    return new IpPortPair(addr.getHostName(), addr.getPort());
  }

  private void doSend(Object msg) {
    if (msg != null) {
      if (!channel.isOpen() || !channel.isActive()) {
        logger.info("send message failed, the channel is closed. msg=[{}]" + msg);
        return;
      }
      ChannelFuture channelFuture = channel.writeAndFlush(msg);
      channelFuture.addListener(new GenericFutureListener<ChannelFuture>() {

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          if (!future.isSuccess()) {
            if (null != future.cause()) {
              logger.info("send mesage failed, reason:" + future.cause());
            } else {
              logger.info("send mesage failed without reason");
            }
          }
        }

      });
    }
  }

}
