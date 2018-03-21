package com.rpc.common.tcp.service;

import com.rpc.common.tcp.domain.AbstractSignal;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author yin.huang
 * @date 2018年3月20日 上午10:12:31
 */
public class TransportUtil {

  private static final AttributeKey<Endpoint> TRANSPORT_ENDPOINT = AttributeKey.valueOf("TRANSPORT_ENDPOINT");

  private static final String                 TRANSPORT_SENDER   = "TRANSPORT_SENDER";

  public static void attachEndpointToChannel(Channel channel, Endpoint endpoint) {
    channel.attr(TRANSPORT_ENDPOINT).set(endpoint);
  }

  public static Endpoint getEndpointOfChannel(Channel channel) {
    return (Endpoint) channel.attr(TRANSPORT_ENDPOINT).get();
  }

  public static Object attachSender(Object object, Sender sender) {
    if (object instanceof AbstractSignal) {
      ((AbstractSignal) object).setProperty(TRANSPORT_SENDER, sender);
    }

    return object;
  }

  public static Sender getSenderOf(Object object) {
    if (object instanceof AbstractSignal) {
      return (Sender) ((AbstractSignal) object).getProperty(TRANSPORT_SENDER);
    }
    return null;
  }

}
