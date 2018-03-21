package com.rpc.common.tcp.service;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.codec.hessian.HessianCodecUtil;
import com.rpc.common.tcp.codec.hessian.HessianDecoder;
import com.rpc.common.tcp.codec.hessian.HessianEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author yin.huang
 * @date 2018年3月19日 上午11:36:53
 */
public class TcpClient implements Sender {

  private static Logger            logger          = LoggerFactory.getLogger(TcpClient.class);

  private String                   connectIp;

  private int                      connectPort;

  private long                     retryTimeout    = 1000;

  private ScheduledExecutorService exec            = Executors.newSingleThreadScheduledExecutor();

  private EventLoopGroup           bossGroup       = null;

  private Bootstrap                bootstrap       = null;

  private EndpointFactory          endpointFactory = new DefaultEndpointFactory();

  private Endpoint                 sender;

  public void start() {

    bossGroup = new NioEventLoopGroup();
    bootstrap = new Bootstrap();
    bootstrap.group(bossGroup);
    bootstrap.channel(NioSocketChannel.class);
    bootstrap.handler(new ChannelInitializer<SocketChannel>() {

      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
        // TODO Auto-generated method stub
        // 编解码
        // kyro
        // ch.pipeline().addLast(new KryoDecoder(new
        // KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance())));
        // ch.pipeline().addLast(new KryoEncoder(new
        // KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance())));

        // hessian
        ch.pipeline().addLast(new HessianDecoder(new HessianCodecUtil()));
        ch.pipeline().addLast(new HessianEncoder(new HessianCodecUtil()));
        ch.pipeline().addLast(new TcpClientHandler());
      }

    });
    doConnect();
  }

  private void doConnect() {
    if (null == connectIp || connectIp.equals("")) {
      logger.info("destIp is null, disable this connector.");
      return;
    }

    ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress(connectIp, connectPort));

    connectFuture.addListener(new GenericFutureListener<ChannelFuture>() {

      @Override
      public void operationComplete(final ChannelFuture future) throws Exception {
        // TODO Auto-generated method stub
        exec.submit(new Runnable() {

          public void run() {
            onConnectComplete(future);
          }
        });
      }

    });
  }

  private void onConnectComplete(ChannelFuture connectFuture) {
    if (!connectFuture.isSuccess()) {
      logger.info("connect [" + this.connectIp + ":" + this.connectPort + "] failed, retry...");
      exec.schedule(new Runnable() {

        public void run() {
          doConnect();
        }
      }, retryTimeout, TimeUnit.MILLISECONDS);
    }
  }

  public void stop() {
    if (bossGroup != null) {
      bossGroup.shutdownGracefully();
    }
  }

  class TcpClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      Endpoint endpoint = TransportUtil.getEndpointOfChannel(ctx.channel());
      if (null != endpoint) {
        endpoint.messageReceived(TransportUtil.attachSender(msg, endpoint));
      } else {
        logger.info("missing endpoint, ignore incoming msg:" + msg);
      }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      Endpoint endpoint = endpointFactory.createEndpoint(ctx.channel());
      if (null != endpoint) {
        TransportUtil.attachEndpointToChannel(ctx.channel(), endpoint);
        sender = endpoint;
      }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      exec.submit(new Runnable() {

        public void run() {
          doConnect();
        }
      });
    }

  }

  @Override
  public void send(Object bean) {
    if (sender != null) {
      sender.send(bean);
    } else {
      logger.info("send: no endpoint, msg [{}] lost. " + bean);
    }
  }

  @Override
  public void send(Object bean, Receiver receiver) {
    if (sender != null) {
      sender.send(bean, receiver);
    } else {
      logger.info("send: no endpoint, msg [{}] lost. " + bean);
    }
  }

  @Override
  public Object sendAndWait(Object bean) {
    if (sender != null) {
      return sender.sendAndWait(bean);
    } else {
      logger.info("sendAndWait: no endpoint, msg [{}] lost. " + bean);
    }
    return null;
  }

  @Override
  public Object sendAndWait(Object bean, long timeout, TimeUnit units) {
    if (sender != null) {
      return sender.sendAndWait(bean, timeout, units);
    } else {
      logger.info("sendAndWait: no endpoint, msg [{}] lost. " + bean);
    }
    return null;
  }

  public void setConnectIp(String connectIp) {
    this.connectIp = connectIp;
  }

  public String getConnectIp() {
    return connectIp;
  }

  public void setConnectPort(int connectPort) {
    this.connectPort = connectPort;
  }

  public int getConnectPort() {
    return connectPort;
  }

  public void setRetryTimeout(long retryTimeout) {
    this.retryTimeout = retryTimeout;
  }

}
