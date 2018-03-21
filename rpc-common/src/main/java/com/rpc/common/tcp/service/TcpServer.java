package com.rpc.common.tcp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.codec.hessian.HessianCodecUtil;
import com.rpc.common.tcp.codec.hessian.HessianDecoder;
import com.rpc.common.tcp.codec.hessian.HessianEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author yin.huang
 * @date 2018年3月19日 上午10:08:31
 */
public class TcpServer {

  private static Logger   logger          = LoggerFactory.getLogger(TcpServer.class);

  private int             maxRetryCount   = 20;

  private long            retryTimeout    = 30 * 1000;                               // 30s

  private String          serverIp        = "0.0.0.0";

  private int             serverPort      = 7777;

  private EndpointFactory endpointFactory = new DefaultEndpointFactory();

  private EventLoopGroup  bossGroup       = null;

  private EventLoopGroup  workGroup       = null;

  public void start() throws InterruptedException {

    bossGroup = new NioEventLoopGroup();
    workGroup = new NioEventLoopGroup();

    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(bossGroup, workGroup);
    bootstrap.channel(NioServerSocketChannel.class);
    bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

      @Override
      protected void initChannel(SocketChannel ch) throws Exception {

        // kyro
        // ch.pipeline().addLast(new KryoDecoder(new
        // KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance())));
        // ch.pipeline().addLast(new KryoEncoder(new
        // KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance())));

        // hessian
        ch.pipeline().addLast(new HessianDecoder(new HessianCodecUtil()));
        ch.pipeline().addLast(new HessianEncoder(new HessianCodecUtil()));

        ch.pipeline().addLast(new TcpServerHandler());
      }
    });
    int retryCount = 0;
    boolean binded = false;
    do {
      try {
        bootstrap.bind(serverIp, serverPort).sync();
        binded = true;
      } catch (InterruptedException e) {
        logger.info("start failed on port:[{}], " + e + ", and retry..." + serverPort);
        // 对绑定异常再次进行尝试
        retryCount++;
        if (retryCount >= maxRetryCount) {
          // 超过最大尝试次数
          throw e;
        }
        try {
          Thread.sleep(retryTimeout);
        } catch (InterruptedException e1) {
        }
      }
    } while (!binded);

    logger.info("server start succeed in " + serverIp + ":" + serverPort);

  }

  public void stop() {
    if (bossGroup != null) {
      bossGroup.shutdownGracefully();
    }
    if (workGroup != null) {
      workGroup.shutdownGracefully();
    }
  }

  class TcpServerHandler extends ChannelInboundHandlerAdapter {

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
      }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
      Endpoint endpoint = TransportUtil.getEndpointOfChannel(ctx.channel());
      if (null != endpoint) {
        endpoint.stop();
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      cause.printStackTrace();
    }
  }

  public void setMaxRetryCount(int maxRetryCount) {
    this.maxRetryCount = maxRetryCount;
  }

  public void setRetryTimeout(long retryTimeout) {
    this.retryTimeout = retryTimeout;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public void setReceiver(Receiver receiver) {
    endpointFactory.setReceiver(receiver);
  }

}
