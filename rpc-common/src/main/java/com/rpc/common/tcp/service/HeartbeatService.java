package com.rpc.common.tcp.service;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.rpc.common.tcp.domain.HeartbeatReq;
import com.rpc.common.tcp.domain.HeartbeatResp;
import com.rpc.common.tcp.domain.ServerGroup;
import com.rpc.common.tcp.domain.ServerSnapshot;
import com.rpc.common.tcp.domain.ServerStore;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午6:13:38
 */
public class HeartbeatService implements Receiver {

  private ServerStore serverStore;

  // 检测服务器存活（秒）
  private int         serverTimeoutMillis = 20;

  @Override
  public void messageReceived(Object msg) {

    if (msg instanceof HeartbeatReq) {
      HeartbeatResp resp = proceedHBReq((HeartbeatReq) msg);
      Sender sender = TransportUtil.getSenderOf(msg);
      sender.send(resp);
    }
  }

  public HeartbeatResp proceedHBReq(HeartbeatReq req) {

    serverStore.refreshServers(req);

    HeartbeatResp resp = new HeartbeatResp();
    resp.setIdentification(req.getIdentification());
    resp.setGroupName(req.getGroupName());

    resp.setCandidates(getCandidateServers(req));

    return resp;
  }

  // 获取注册中心服务器列表
  public ArrayList<ServerSnapshot> getCandidateServers(HeartbeatReq hb) {

    ArrayList<ServerSnapshot> groups = new ArrayList<ServerSnapshot>();

    String routerName = hb.getRouterName();
    if (StringUtils.isBlank(routerName)) {
      return groups;
    }

    ServerGroup serverGroup = serverStore.getServerGroupByName(routerName.trim());
    if (serverGroup == null) {
      return groups;
    }

    for (ServerSnapshot serverSnapshot : serverGroup.getServers().values()) {
      if (serverSnapshot.isRunning(serverTimeoutMillis)) {
        groups.add(serverSnapshot);
      }
    }

    return groups;
  }

  public void setServerStore(ServerStore serverStore) {
    this.serverStore = serverStore;
  }

  public void setServerTimeoutMillis(int serverTimeoutMillis) {
    this.serverTimeoutMillis = serverTimeoutMillis;
  }

}
