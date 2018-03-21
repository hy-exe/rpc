package com.rpc.common.tcp.domain;

/**
 * @author yin.huang
 * @date 2018年3月19日 下午3:55:10
 */
public class HeartbeatReq extends AbstractSignal {

  /**
   * 
   */
  private static final long serialVersionUID = -1275909714172800575L;

  private String            groupName;

  private String            routerName;

  private ServerSnapshot    serverSnapshot   = new ServerSnapshot();

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getRouterName() {
    return routerName;
  }

  public void setRouterName(String routerName) {
    this.routerName = routerName;
  }

  public ServerSnapshot getServerSnapshot() {
    return serverSnapshot;
  }

  public void setServerSnapshot(ServerSnapshot serverSnapshot) {
    this.serverSnapshot = serverSnapshot;
  }

}
