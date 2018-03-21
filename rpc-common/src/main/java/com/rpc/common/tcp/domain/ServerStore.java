package com.rpc.common.tcp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午6:17:05 服务商店
 */
public class ServerStore implements Serializable {

  /**
   * 
   */
  private static final long                  serialVersionUID = -86640330211107012L;

  // 服务器组
  private ConcurrentMap<String, ServerGroup> serverGroups     = new ConcurrentHashMap<String, ServerGroup>();

  /**
   * 刷新服务器组
   * 
   * @param req
   */
  public void refreshServers(HeartbeatReq req) {
    String groupName = req.getGroupName();
    ServerGroup group = serverGroups.get(groupName);
    if (group == null) {
      group = new ServerGroup(groupName);
      serverGroups.put(groupName, group);
    }

    ServerSnapshot server = req.getServerSnapshot();

    ServerSnapshot snapshot = null;
    IpPortPair key = new IpPortPair(server.getIp(), server.getPort());
    if (group.getServers().containsKey(key)) {// 已经存在，更新心跳以及活动时间
      snapshot = group.getServers().get(key);
      snapshot.setHeartbeatTime(System.currentTimeMillis());
    } else {
      snapshot = new ServerSnapshot();// 初始化系统启动时间
    }
    snapshot.setIp(server.getIp());
    snapshot.setPort(server.getPort());
    group.getServers().put(key, snapshot);
  }

  public List<ServerSnapshot> getServerSnapshot(String groupName) {
    List<ServerSnapshot> ret = new ArrayList<ServerSnapshot>();
    ServerGroup serverDomain = serverGroups.get(groupName);
    if (serverDomain == null) {
      return ret;
    }
    ret.addAll(serverDomain.getServers().values());
    Collections.sort(ret);
    return ret;
  }

  public ServerSnapshot getServerSnapshotByAddress(IpPortPair address) {
    ServerSnapshot ret = null;
    Collection<ServerGroup> domains = serverGroups.values();
    for (ServerGroup domain : domains) {
      for (ServerSnapshot snapshot : domain.getServers().values()) {
        IpPortPair server = new IpPortPair(snapshot.getIp(), snapshot.getPort());
        if (server.equals(address)) {
          ret = snapshot;
          break;
        }
      }
    }
    return ret;
  }

  public ServerSnapshot[] getServerSnapshotByAll() {
    List<ServerSnapshot> ret = new ArrayList<ServerSnapshot>();
    for (ServerGroup domain : serverGroups.values()) {
      for (ServerSnapshot snapshot : domain.getServers().values()) {
        ret.add(snapshot);
      }
    }
    Collections.sort(ret);
    return ret.toArray(new ServerSnapshot[0]);
  }

  public ConcurrentMap<String, ServerGroup> getServerGroups() {
    return serverGroups;
  }

  public void setServerGroups(ConcurrentMap<String, ServerGroup> serverGroups) {
    this.serverGroups = serverGroups;
  }

  public ServerGroup getServerGroupByName(String groupName) {
    return serverGroups.get(groupName);
  }

}
