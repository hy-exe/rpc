package com.rpc.common.tcp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rpc.common.tcp.domain.HeartbeatResp;
import com.rpc.common.tcp.domain.IpPortPair;
import com.rpc.common.tcp.domain.ServerSnapshot;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午3:53:37
 */
public class HeartbeatProducer {

  private static Logger            logger            = LoggerFactory.getLogger(HeartbeatProducer.class);

  private HeartbeatMessageProducer messageProducer;

  private TcpClient                connector;

  private Timer                    timer             = new Timer();

  private long                     heartbeatInterval = 5000L;

  private TcpRouter                router;

  public void start() {

    timer.scheduleAtFixedRate(new TimerTask() {

      public void run() {

        connector.send(messageProducer.getHeartbeatReq(), new Receiver() {

          @Override
          public void messageReceived(Object resp) {

            if (null != router) {
              HeartbeatResp heartbeatResp = (HeartbeatResp) resp;
              List<ServerSnapshot> groups = heartbeatResp.getCandidates();
              if (groups != null && groups.size() > 0) {
                List<IpPortPair> infos = new ArrayList<IpPortPair>();
                for (ServerSnapshot server : groups) {
                  infos.add(new IpPortPair(server.getIp(), server.getPort()));
                }
                router.doRefreshRoute(infos);
              }
            }
          }
        });
      }
    }, 0L, heartbeatInterval);
  }

  public void setMessageProducer(HeartbeatMessageProducer messageProducer) {
    this.messageProducer = messageProducer;
  }

  public void setConnector(TcpClient connector) {
    this.connector = connector;
  }

  public void setHeartbeatInterval(long heartbeatInterval) {
    this.heartbeatInterval = heartbeatInterval;
  }

  public void setRouter(TcpRouter router) {
    this.router = router;
  }

}
